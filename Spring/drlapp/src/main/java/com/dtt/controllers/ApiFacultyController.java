package com.dtt.controllers;

import com.dtt.pojo.Faculty;
import com.dtt.services.FacultyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @GetMapping("/faculties")
    public List<Faculty> getAllFaculties() {
        return facSer.getAllFaculties();
    }
    
    @DeleteMapping("/faculties/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyFaculty(@PathVariable("id") int id){
        this.facSer.deleteFaculty(id);
    }

}
