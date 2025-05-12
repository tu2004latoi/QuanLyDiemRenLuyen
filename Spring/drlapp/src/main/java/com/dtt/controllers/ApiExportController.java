/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Student;
import com.dtt.pojo.User;
import com.dtt.services.ExportService;
import com.dtt.services.StatisticsService;
import com.dtt.services.UserService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api/export")
public class ApiExportController {

    @Autowired
    private UserService userService; // Service lấy danh sách người dùng

    @Autowired
    private ExportService exportService; // Service xử lý xuất file
    
    @Autowired
    private StatisticsService sttSer;

    @GetMapping("/csv")
    public void exportCSV(@RequestParam Map<String, String> params, HttpServletResponse response) throws IOException {
        List<Student> students = sttSer.getStatistics(params);
        exportService.exportCSV(students, response);
    }

    @GetMapping("/pdf")
    public void exportPDF(@RequestParam Map<String, String> params, HttpServletResponse response) throws IOException, DocumentException {
        List<Student> students = sttSer.getStatistics(params);
        exportService.exportPDF(students, response);
    }
}
