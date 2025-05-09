/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.MissingReport;
import com.dtt.pojo.TrainingPoint;
import com.dtt.pojo.User;
import com.dtt.services.MissingReportService;
import com.dtt.services.TrainingPointService;
import com.dtt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api")
public class ApiMissingReportController {
    @Autowired
    private MissingReportService mrSer;
    
    @Autowired
    private TrainingPointService tpSer;
    
    @Autowired
    private UserService userSer;
    
    @PatchMapping("/missing-reports/confirm/{id}")
    public ResponseEntity<?> comfirmMissingReport(@PathVariable("id") int id) throws Exception{
        MissingReport mr = this.mrSer.getMissingReportById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User u = this.userSer.getUserByUsername(username);
        this.tpSer.confirmTrainingPointById(mr.getTrainingPoint().getId(), u);
        mr.setStatus(MissingReport.ReportStatus.CONFIRMED);
        this.mrSer.addOrUpdateMissingReport(mr);
        
        return ResponseEntity.ok("Successfully");
    }
    
    @PatchMapping("/missing-reports/reject/{id}")
    public ResponseEntity<?> rejectMissingReport(@PathVariable("id") int id) throws Exception{
        MissingReport mr = this.mrSer.getMissingReportById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User u = this.userSer.getUserByUsername(username);
        this.tpSer.rejectTrainingPointById(mr.getTrainingPoint().getId(), u);
        mr.setStatus(MissingReport.ReportStatus.REJECTED);
        this.mrSer.addOrUpdateMissingReport(mr);
        
        return ResponseEntity.ok("Successfully");
    }
    
    @PatchMapping("/missing-reports/reject-after-confirm/{id}")
    public ResponseEntity<?> rejectAfterConfirmMissingReport(@PathVariable("id") int id) throws Exception{
        MissingReport mr = this.mrSer.getMissingReportById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User u = this.userSer.getUserByUsername(username);
        this.tpSer.rejectAfterApprovedTrainingPointById(mr.getTrainingPoint().getId(), u);
        mr.setStatus(MissingReport.ReportStatus.REJECTED);
        this.mrSer.addOrUpdateMissingReport(mr);
        
        return ResponseEntity.ok("Successfully");
    }
}
