/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Student;
import com.dtt.services.StudentService;
import com.dtt.services.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getAllStudents() {
        List<Student> students = this.stSer.getAllStudents();
        List<Map<String, Object>> listData = new ArrayList<>();
        for (Student s : students) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", s.getUser().getId());
            data.put("email", s.getUser().getEmail());
            data.put("studentName", s.getUser().getName());
            data.put("studentId", s.getStudentId());
            data.put("classroom", s.getClassRoom().getName());
            data.put("facultyId", s.getFaculty().getId());
            data.put("facultyName", s.getFaculty().getName());
            data.put("avatar", s.getUser().getAvatar());
            data.put("classify", s.getClassify());
            data.put("point_1", s.getUser().getPoint_1());
            data.put("point_2", s.getUser().getPoint_2());
            data.put("point_3", s.getUser().getPoint_3());
            data.put("point_4", s.getUser().getPoint_4());
            data.put("totalPoint", s.getUser().totalPoint());

            listData.add(data);
        }

        return ResponseEntity.ok(listData);
    }

    @GetMapping("/users/students/{id}")
    public ResponseEntity<?> getStudentDetails(@PathVariable("id") int id) {
        Student s = this.stSer.getStudentByUserId(id);
        Map<String, Object> data = new HashMap<>();
        data.put("id", s.getUser().getId());
        data.put("email", s.getUser().getEmail());
        data.put("studentName", s.getUser().getName());
        data.put("studentId", s.getStudentId());
        data.put("classroom", s.getClassRoom().getName());
        data.put("facultyId", s.getFaculty().getId());
        data.put("facultyName", s.getFaculty().getName());
        data.put("avatar", s.getUser().getAvatar());
        data.put("classify", s.getClassify());
        data.put("point_1", s.getUser().getPoint_1());
        data.put("point_2", s.getUser().getPoint_2());
        data.put("point_3", s.getUser().getPoint_3());
        data.put("point_4", s.getUser().getPoint_4());
        data.put("totalPoint", s.getUser().totalPoint());

        return ResponseEntity.ok(data);
    }

    @PostMapping("/user/students/{id}")
    public void addStudent(Student st) {
        this.stSer.addOrUpdateStudent(st);
    }
}
