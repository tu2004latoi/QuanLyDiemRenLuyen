/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Student;
import com.dtt.services.FacultyService;
import com.dtt.services.StatisticsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author MR TU
 */
@Controller
public class StatisticsController {
    @Autowired
    private StatisticsService sttSer;
    
    @Autowired
    private FacultyService facSer;
    
    @GetMapping("/statistics")
    public String StatisticsView(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("students", this.sttSer.getStatistics(params));
        model.addAttribute("faculty", this.facSer.getAllFaculties());
        
        return "statistics";
    }
}
