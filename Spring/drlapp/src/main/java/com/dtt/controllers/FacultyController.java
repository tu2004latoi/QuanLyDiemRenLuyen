/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Faculty;
import com.dtt.services.FacultyService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author MR TU
 */
@Controller
public class FacultyController {
    @Autowired
    private FacultyService facSer;
    
    @GetMapping("/faculties")
    private String facultiesView(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("faculties", this.facSer.getFaculties(params));
        
        return "faculties";
    }
    
    @GetMapping("/faculties/add")
    private String manageFaculty(Model model){
        Faculty f = new Faculty();
        
        model.addAttribute("faculty", f);
        
        return "addFaculty";
    }
    
    @PostMapping("/faculties/add")
    private String addFaculty(@ModelAttribute("faculty") @Valid Faculty f){
        this.facSer.addOrUpdateFaculty(f);
        
        return "redirect:/faculties";
    }
}
