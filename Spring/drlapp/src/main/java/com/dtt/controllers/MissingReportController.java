/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Activity;
import com.dtt.pojo.MissingReport;
import com.dtt.pojo.TrainingPoint;
import com.dtt.pojo.User;
import com.dtt.services.ActivityService;
import com.dtt.services.MissingReportService;
import com.dtt.services.TrainingPointService;
import com.dtt.services.UserService;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author MR TU
 */
@Controller
public class MissingReportController {
    @Autowired
    private MissingReportService mrSer;
    
    @Autowired
    private UserService userSer;
    
    @Autowired
    private ActivityService activitySer;
    
    @Autowired
    private TrainingPointService tpSer;
    
    @GetMapping("/missing-reports") 
    public String missingReportsView(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("mr", this.mrSer.getMissingReports(params));
        
        return "missingReport";
    }
    @PostMapping("/missing-reports/create")
    public String createMissingReport(@RequestParam("userId") int userId,
            @RequestParam("activityId") int activityId,
            @RequestParam("point") int point,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes){
        
        User u = this.userSer.getUserById(userId);
        Activity a = this.activitySer.getActivityById(activityId);
        TrainingPoint t = this.tpSer.getTrainingPointByUserIdAndActivityId(userId, activityId);
        MissingReport mr = new MissingReport();
        mr.setUser(u);
        mr.setActivity(a);
        mr.setPoint(point);
        mr.setTrainingPoint(t);
        mr.setFile(file);
        mr.setDateReport(LocalDateTime.now());
        mr.setStatus(MissingReport.ReportStatus.PENDING);
        
        this.mrSer.addOrUpdateMissingReport(mr);
        
        redirectAttributes.addFlashAttribute("msg", "Báo thiếu đã được gửi.");
        return "redirect:/my-activities";
    }
}
