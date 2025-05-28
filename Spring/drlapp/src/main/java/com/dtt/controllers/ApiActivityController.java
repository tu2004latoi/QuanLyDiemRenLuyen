/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Activity;
import com.dtt.pojo.ActivityRegistrations;
import com.dtt.pojo.Faculty;
import com.dtt.pojo.User;
import com.dtt.services.ActivityRegistrationService;
import com.dtt.services.ActivityService;
import com.dtt.services.EmailService;
import com.dtt.services.FacultyService;
import com.dtt.services.TrainingPointService;
import com.dtt.services.UserService;
import jakarta.ws.rs.core.MediaType;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api")
public class ApiActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRegistrationService arSer;

    @Autowired
    private UserService userSer;

    @Autowired
    private TrainingPointService trainingPointSer;

    @Autowired
    private EmailService emailSer;

    @Autowired
    private FacultyService facultySer;

    //Toàn bộ hoạt động API
    @GetMapping("/activities")
    public List<Activity> getAllActivities(@RequestParam Map<String, String> params) {
        return activityService.getActivities(params);
    }

    //Chi tiết 1 hoạt động API
    @GetMapping("/activities/{id}")
    public Activity getActivityById(@PathVariable("id") int id) {
        return activityService.getActivityById(id);
    }

    //Xóa 1 hoạt động
    @DeleteMapping("/activities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String destroy(@PathVariable("id") int id, Principal principal) {
        User u = userSer.getUserByUsername(principal.getName());
        if (u.getRole() == User.Role.STUDENT) {
            throw new AccessDeniedException("Sinh viên không được phép xóa");
        }
        this.activityService.deleteActivity(id);

        return "redirect:/activities";
    }

    //Đăng ký hoạt động
    @PostMapping("/activities/{id}")
    public ResponseEntity<String> registerActivity(@PathVariable(value = "id") Integer activityId,
            Principal principal, RedirectAttributes redirectAttributes) {
        try {
            String username = principal.getName();
            User user = userSer.getUserByUsername(username);
            Activity a = this.activityService.getActivityById(activityId);
            if (a.getStatus() != Activity.ActivityStatus.UPCOMING
                    && a.getStatus() != Activity.ActivityStatus.ONGOING) {
                redirectAttributes.addFlashAttribute("message", "Hoạt động không còn mở đăng ký!");
                return new ResponseEntity<>("Hoạt động không còn mở đăng ký!", HttpStatus.BAD_REQUEST);
            }

            if (a.getCurrentParticipants() == a.getMaxParticipants()) {
                redirectAttributes.addFlashAttribute("message", "Hoạt động đã đủ số người đăng ký!");
                return new ResponseEntity<>("Hoạt động đã đủ số người đăng ký!", HttpStatus.BAD_REQUEST);
            }

            arSer.registerToActivity(user.getId(), activityId);
            String subject = "Xác nhận đăng ký hoạt động";
            String content = "Xin chào " + user.getName() + ",\n\n"
                    + "Bạn đã đăng ký thành công hoạt động: " + a.getName() + ".\n"
                    + "Thời gian: " + a.getStartDate() + " - " + a.getEndDate() + "\n"
                    + "Địa điểm: " + a.getLocation() + "\n\n"
                    + "Trân trọng!";
            System.out.println("Sending email to: " + user.getEmail());
            emailSer.sendEmail(user.getEmail(), subject, content);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>("Lỗi hệ thống: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Thêm 1 hoạt động
    @PostMapping(path = "/activities", consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity<String> addActivity(
            @ModelAttribute Activity a,
            @RequestParam("facultyId") Integer facultyId,
            Principal principal) {

        User u = userSer.getUserByUsername(principal.getName());
        if (u.getRole() == User.Role.STUDENT) {
            throw new AccessDeniedException("Sinh viên không được phép thêm hoạt động");
        }

        a.setOrganizer(u);

        Faculty f = facultySer.getFacultyById(facultyId);
        a.setFaculty(f);

        this.activityService.addOrUpdateActivity(a);

        // Trả về chuỗi đơn giản
        return ResponseEntity.status(HttpStatus.CREATED).body("Hoạt động đã được tạo thành công!");
    }

    //Cập nhật 1 hoạt động
    @PutMapping(path = "/activities/{id}", consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity<String> updateActivity(
            @PathVariable("id") int id,
            @ModelAttribute Activity a,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("facultyId") Integer facultyId,
            Principal principal) {

        User u = userSer.getUserByUsername(principal.getName());

        if (u.getRole() == User.Role.STUDENT) {
            throw new AccessDeniedException("Sinh viên không được phép cập nhật hoạt động");
        }

        Activity existingActivity = activityService.getActivityById(id);
        if (existingActivity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hoạt động không tồn tại");
        }

        // Cập nhật các trường từ a
        existingActivity.setName(a.getName());
        existingActivity.setDescription(a.getDescription());
        existingActivity.setStartDate(a.getStartDate());
        existingActivity.setEndDate(a.getEndDate());
        existingActivity.setLocation(a.getLocation());
        existingActivity.setMaxParticipants(a.getMaxParticipants());
        existingActivity.setActive(a.getActive());
        existingActivity.setStatus(a.getStatus());
        existingActivity.setPointType(a.getPointType());
        existingActivity.setPointValue(a.getPointValue());

        // ✅ Gán file upload nếu có
        if (file != null && !file.isEmpty()) {
            existingActivity.setFile(file); // Quan trọng
        }

        // Gán khoa và người tổ chức
        Faculty f = facultySer.getFacultyById(facultyId);
        existingActivity.setFaculty(f);
        existingActivity.setOrganizer(u);

        // Gọi service
        activityService.addOrUpdateActivity(existingActivity);

        return ResponseEntity.ok("Hoạt động đã được cập nhật thành công");
    }

}
