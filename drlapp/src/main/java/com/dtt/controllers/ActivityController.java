/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Activity;
import com.dtt.pojo.Faculty;
import com.dtt.services.ActivityService;
import com.dtt.services.FacultyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    
    @GetMapping("/activities")
    public String manageActivity(Model model){
        model.addAttribute("activity", new Activity());
       
        List<Faculty> faculties = facultySer.getAllFaculties();
        model.addAttribute("faculties", faculties);
        return "activities";
    }
    
    @PostMapping("/add")
    public String addActivity(@ModelAttribute("activity") Activity a){
        this.activitySer.addOrUpdateActivity(a);
        
        return "redirect:/";
    }
}
