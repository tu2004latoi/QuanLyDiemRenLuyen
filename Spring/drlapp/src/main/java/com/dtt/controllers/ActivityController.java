/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Activity;
import com.dtt.pojo.Faculty;
import com.dtt.pojo.User;
import com.dtt.secutiry.CustomUserDetails;
import com.dtt.services.ActivityService;
import com.dtt.services.FacultyService;
import com.dtt.services.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author MR TU
 */
@Controller
public class ActivityController {

    @Autowired
    private ActivityService activitySer;

    @Autowired
    private FacultyService facultySer;

    @Autowired
    private UserService userSer;

    @GetMapping("/activities")
    public String manageActivity(Model model) {
        System.out.println("==> Đã vào controller addActivity()");
        Activity activity = new Activity();
        activity.setFaculty(new Faculty()); // ⭐ Khởi tạo để tránh null
        model.addAttribute("activity", activity);

        List<Faculty> faculties = facultySer.getAllFaculties();
        model.addAttribute("faculties", faculties);

        return "activities";
    }

    @GetMapping("/activities/{id}")
    public String updateActivity(Model model, @PathVariable("id") int id) {
        model.addAttribute("activity", this.activitySer.getActivityById(id));

        List<Faculty> faculties = facultySer.getAllFaculties();
        model.addAttribute("faculties", faculties);
        return "activities";
    }

    @GetMapping("/activities/details/{id}")
    public String activityDetails(Model model, @PathVariable("id") int id) {
        Activity a = activitySer.getActivityById(id);
        model.addAttribute("activity", a);
        return "activityDetails";
    }

    @PostMapping("/add")
    public String addActivity(@ModelAttribute("activity") @Valid Activity a, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", facultySer.getAllFaculties());
            return "activities";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Integer userId = userDetails.getId();

        // Gán người tổ chức
        User user = this.userSer.getUserById(userId);
        a.setOrganizer(user);

        // Lưu hoạt động
        this.activitySer.addOrUpdateActivity(a);

        return "redirect:/";  // Chuyển hướng sau khi lưu thành công
    }

}
