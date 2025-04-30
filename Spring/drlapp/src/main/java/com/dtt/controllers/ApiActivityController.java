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
import com.dtt.services.TrainingPointService;
import com.dtt.services.UserService;
import jakarta.ws.rs.core.MediaType;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    //Toàn bộ hoạt động API
    @GetMapping("/activities")
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    //Chi tiết 1 hoạt động API
    @GetMapping("/activities/{id}")
    public Activity getActivityById(@PathVariable("id") int id) {
        return activityService.getActivityById(id);
    }

    //Xóa 1 hoạt động
    @DeleteMapping("/activities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String destroy(@PathVariable("id") int id) {
        this.activityService.deleteActivity(id);

        return "redirect:/activities";
    }

    //Đăng ký hoạt động
    @PostMapping("/activities/{id}")
    public ResponseEntity<String> registerActivity(@PathVariable(value = "id") Integer activityId,
            Principal principal) {
        try {
            String username = principal.getName();
            User user = userSer.getUserByUsername(username);
            arSer.registerToActivity(user.getId(), activityId);
            
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>("Lỗi hệ thống: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Thêm 1 hoạt động
    @PostMapping(path = "/activities", consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity<Activity> addAcitivity(@ModelAttribute Activity a) {
        Activity saved = this.activityService.addOrUpdateActivity(a);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

}
