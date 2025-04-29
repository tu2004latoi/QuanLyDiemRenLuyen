/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.services.ActivityService;
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
    private ActivityService activitySer;
    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params){
        //Activity
        model.addAttribute("activities", this.activitySer.getActivities(params));
        return "index";
    }
}
