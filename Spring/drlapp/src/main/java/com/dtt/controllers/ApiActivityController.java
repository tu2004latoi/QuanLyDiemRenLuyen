/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Activity;
import com.dtt.services.ActivityService;
import jakarta.ws.rs.core.MediaType;
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

    @GetMapping("/activities")
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }
    
    @GetMapping("/activities/{id}")
    public Activity getActivityById(@PathVariable("id") int id){
        return activityService.getActivityById(id);
    }

    @DeleteMapping("/activities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String destroy(@PathVariable("id") int id) {
        this.activityService.deleteActivity(id);
        
        return "redirect:/activities";
    }

    @PostMapping(path = "/activities", consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity<Activity> addAcitivity(@ModelAttribute Activity a){
        Activity saved = this.activityService.addOrUpdateActivity(a);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
