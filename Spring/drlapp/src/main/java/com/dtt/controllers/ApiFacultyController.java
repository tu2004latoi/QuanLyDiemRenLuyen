package com.dtt.controllers;

import com.dtt.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api")
public class ApiFacultyController {
    @Autowired
    private FacultyService facSer;
    
    
}
