/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.services.ActivityService;
import com.dtt.services.NewsFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author MR TU
 */
@Controller
public class IndexController {
    @Autowired
    private NewsFeedService newsFeedSer;
    
    @Autowired
    private ActivityService activitySer;
    @RequestMapping("/")
    public String index(Model model){
        //NewsFeed
        model.addAttribute("newsFeeds",newsFeedSer.getAllNewsFeeds());
        
        //Activity
        model.addAttribute("activities",activitySer.getAllActivities());
        return "index";
    }
}
