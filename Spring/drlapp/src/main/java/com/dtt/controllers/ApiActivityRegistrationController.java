/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Activity;
import com.dtt.pojo.ActivityRegistrations;
import com.dtt.pojo.User;
import com.dtt.services.ActivityRegistrationService;
import com.dtt.services.ActivityService;
import com.dtt.services.EmailService;
import com.dtt.services.UserService;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api")
public class ApiActivityRegistrationController {

    @Autowired
    private ActivityRegistrationService arSer;

    @Autowired
    private EmailService emailSer;

    @Autowired
    private ActivityService activitySer;

    @Autowired
    private UserService userSer;

    @DeleteMapping("/users/activity-registration/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String destroyActivityRegistration(@PathVariable("id") int id) {
        this.arSer.deleteActivityRegistrationById(id);

        return "redirect:/user/activity-registration";
    }

    @PostMapping("/users/activity-registration") //Đăng ký hoạt động
    @ResponseStatus(HttpStatus.CREATED)
    public ActivityRegistrations registerActivity(@RequestBody Map<String, Integer> payload) {
        int userId = payload.get("userId");
        int activityId = payload.get("activityId");
        this.arSer.registerToActivity(userId, activityId);
        Activity a = this.activitySer.getActivityById(activityId);
        User user = this.userSer.getUserById(userId);
        String subject = "Xác nhận đăng ký hoạt động";
        String content = "Xin chào " + user.getName() + ",\n\n"
                + "Bạn đã đăng ký thành công hoạt động: " + a.getName() + ".\n"
                + "Thời gian: " + a.getStartDate() + " - " + a.getEndDate() + "\n"
                + "Địa điểm: " + a.getLocation() + "\n\n"
                + "Trân trọng!";
        System.out.println("Sending email to: " + user.getEmail());
        emailSer.sendEmail(user.getEmail(), subject, content);
        return null;
    }

    @GetMapping("/activity-registrations")
    public ResponseEntity<?> activityRegistrationView() {
        List<ActivityRegistrations> activityRegistrations = this.arSer.getAllActivityRegistrations();
        List<Map<String, Object>> listData = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (ActivityRegistrations ar : activityRegistrations) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", ar.getId());
            data.put("userId", ar.getUser().getId());
            data.put("activityId", ar.getActivity().getId());
            data.put("registrationDate", ar.getRegistrationDate().format(formatter));
            data.put("isConfirm", ar.isIsConfirm());

            listData.add(data);
        }

        return ResponseEntity.ok(listData);
    }
}
