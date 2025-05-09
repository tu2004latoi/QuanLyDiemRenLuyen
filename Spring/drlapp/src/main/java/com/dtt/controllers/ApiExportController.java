/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.User;
import com.dtt.services.ExportService;
import com.dtt.services.UserService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/csv")
    public void exportCSV(HttpServletResponse response) throws IOException {
        List<User> users = userService.getAllStudents();
        exportService.exportCSV(users, response);
    }

    @GetMapping("/pdf")
    public void exportPDF(HttpServletResponse response) throws IOException, DocumentException {
        List<User> users = userService.getAllStudents();
        exportService.exportPDF(users, response);
    }
}
