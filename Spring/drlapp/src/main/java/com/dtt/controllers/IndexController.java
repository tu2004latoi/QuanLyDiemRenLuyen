/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.services.ActivityService;
import com.dtt.services.UserService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author MR TU
 */
@Controller
public class IndexController {
    @Autowired
    private ActivityService activityService;
    
    @Autowired
    private UserService userSer;
    
    @RequestMapping("/")
    public String showDashboard(Model model) {
        Map<String, Object> stats = new HashMap<>();

        // Lấy dữ liệu từ service hoặc repository
        stats.put("totalUsers", userSer.getCountUsers());
        stats.put("totalActivities", activityService.getCountActivities());
//        stats.put("totalPoints", trainingPointService.sumAllPoints());
//        stats.put("totalReports", reportService.countAll());

        model.addAttribute("stats", stats);
        return "index";
    }
}
