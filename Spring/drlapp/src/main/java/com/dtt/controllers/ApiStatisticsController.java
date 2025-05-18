/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Student;
import com.dtt.services.StatisticsService;
import com.dtt.services.StudentService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api")
public class ApiStatisticsController {

    @Autowired
    private StatisticsService sttSer;

    @Autowired
    private StudentService stSer;

    @GetMapping("/statistics")
    public ResponseEntity<?> getStatisticsData(@RequestParam Map<String, String> params) {
        List<Student> students = this.sttSer.getStatistics(params);

        // Đếm số lượng theo phân loại (classify)
        Map<String, Long> achievementCounts = students.stream()
                .collect(Collectors.groupingBy(Student::getClassify, Collectors.counting()));

        // Chuẩn bị danh sách sinh viên ở dạng map
        List<Map<String, Object>> studentDataList = new ArrayList<>();
        for (Student s : students) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", s.getId());
            data.put("name", s.getUser().getName());
            data.put("email", s.getUser().getEmail());
            data.put("studentCode", s.getStudentId());
            data.put("point_1",s.getUser().getPoint_1());
            data.put("point_2",s.getUser().getPoint_2());
            data.put("point_3",s.getUser().getPoint_3());
            data.put("point_4",s.getUser().getPoint_4());
            data.put("point_total",s.getUser().totalPoint());
            data.put("classify", s.getClassify());
            data.put("faculty", s.getFaculty() != null ? s.getFaculty().getName() : null);
            data.put("class", s.getClassRoom()!= null ? s.getClassRoom().getName() : null);
            // Thêm các trường khác nếu cần
            studentDataList.add(data);
        }

        // Trả về kết quả gồm danh sách sinh viên và thống kê phân loại
        Map<String, Object> response = new HashMap<>();
        response.put("students", studentDataList);
        response.put("achievementCounts", achievementCounts);

        return ResponseEntity.ok(response);
    }

}
