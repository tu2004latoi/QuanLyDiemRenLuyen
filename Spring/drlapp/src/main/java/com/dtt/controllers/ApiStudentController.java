/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Student;
import com.dtt.services.StudentService;
import com.dtt.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api")
public class ApiStudentController {
    @Autowired
    private UserService userSer;
    
    @Autowired
    private StudentService stSer;
    
    @GetMapping("/users/students") 
    public List<Student> getAllStudents(){
        return this.stSer.getAllStudents();
    }
    
    @GetMapping("/users/students/{id}") 
    public Student getAllStudents(@PathVariable("id") int id){
        return this.stSer.getStudentByUserId(id);
    }
    
    @PostMapping("/user/students/{id}")
    public void addStudent(Student st){
        this.stSer.addOrUpdateStudent(st);
    }
}
