/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Activity;
import com.dtt.pojo.MissingReport;
import com.dtt.pojo.Notification;
import com.dtt.pojo.TrainingPoint;
import com.dtt.pojo.User;
import com.dtt.services.ActivityService;
import com.dtt.services.MissingReportService;
import com.dtt.services.NotificationService;
import com.dtt.services.TrainingPointService;
import com.dtt.services.UserService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    private ActivityService activitySer;

    @Autowired
    private NotificationService noSer;

    @PatchMapping("/missing-reports/confirm/{id}")
    public ResponseEntity<?> comfirmMissingReport(@PathVariable("id") int id) throws Exception {
        MissingReport mr = this.mrSer.getMissingReportById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User u = this.userSer.getUserByUsername(username);
        this.tpSer.confirmTrainingPointById(mr.getTrainingPoint().getId(), u);
        mr.setStatus(MissingReport.ReportStatus.CONFIRMED);
        this.mrSer.addOrUpdateMissingReport(mr);
        MissingReport m = this.mrSer.getMissingReportById(id);
        User user = this.userSer.getUserById(m.getUser().getId());
        Notification n = new Notification();
        n.setUser(user);
        n.setContent("Biếu thiếu của bạn được chấp nhận");
        n.setIsRead(false);
        n.setCreatedAt(LocalDateTime.now());
        this.noSer.addNotification(n);

        return ResponseEntity.ok("Successfully");
    }

    @PatchMapping("/missing-reports/reject/{id}")
    public ResponseEntity<?> rejectMissingReport(@PathVariable("id") int id) throws Exception {
        MissingReport mr = this.mrSer.getMissingReportById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User u = this.userSer.getUserByUsername(username);
        this.tpSer.rejectTrainingPointById(mr.getTrainingPoint().getId(), u);
        mr.setStatus(MissingReport.ReportStatus.REJECTED);
        this.mrSer.addOrUpdateMissingReport(mr);
        MissingReport m = this.mrSer.getMissingReportById(id);
        User user = this.userSer.getUserById(m.getUser().getId());
        Notification n = new Notification();
        n.setUser(user);
        n.setContent("Biếu thiếu của bạn bị từ chối");
        n.setIsRead(false);
        n.setCreatedAt(LocalDateTime.now());
        this.noSer.addNotification(n);

        return ResponseEntity.ok("Successfully");
    }

    @PatchMapping("/missing-reports/reject-after-confirm/{id}")
    public ResponseEntity<?> rejectAfterConfirmMissingReport(@PathVariable("id") int id) throws Exception {
        MissingReport mr = this.mrSer.getMissingReportById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User u = this.userSer.getUserByUsername(username);
        this.tpSer.rejectAfterApprovedTrainingPointById(mr.getTrainingPoint().getId(), u);
        mr.setStatus(MissingReport.ReportStatus.REJECTED);
        this.mrSer.addOrUpdateMissingReport(mr);
        MissingReport m = this.mrSer.getMissingReportById(id);
        User user = this.userSer.getUserById(m.getUser().getId());
        Notification n = new Notification();
        n.setUser(user);
        n.setContent("Biếu thiếu của bạn bị từ chối");
        n.setIsRead(false);
        n.setCreatedAt(LocalDateTime.now());
        this.noSer.addNotification(n);

        return ResponseEntity.ok("Successfully");
    }

    @PostMapping("/missing-reports/create")
    public ResponseEntity<?> createMissingReport(@RequestParam("userId") int userId,
            @RequestParam("activityId") int activityId,
            @RequestParam("point") int point,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

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
        return ResponseEntity.ok("Successfuly to create");
    }

    @GetMapping("/missing-reports")
    public ResponseEntity<?> missingReportsView(@RequestParam Map<String, String> params) {
        List<MissingReport> listMissingReports = this.mrSer.getMissingReports(params);
        List<Map<String, Object>> listData = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (MissingReport mr : listMissingReports) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", mr.getId());
            data.put("userId", mr.getUser().getId());
            data.put("activityId", mr.getActivity().getId());
            data.put("trainingPointId", mr.getTrainingPoint().getId());
            data.put("point", mr.getPoint());
            data.put("dateReport", mr.getDateReport().format(formatter));
            data.put("image", mr.getImage());
            data.put("status", mr.getStatus());

            listData.add(data);
        }

        return ResponseEntity.ok(listData);
    }

    @GetMapping("/missing-reports/{id}")
    public ResponseEntity<?> missingReportDetailsView(@PathVariable("id") int id) {
        MissingReport mr = this.mrSer.getMissingReportById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> data = new HashMap<>();
        data.put("id", mr.getId());
        data.put("userId", mr.getUser().getId());
        data.put("activityId", mr.getActivity().getId());
        data.put("trainingPointId", mr.getTrainingPoint().getId());
        data.put("point", mr.getPoint());
        data.put("dateReport", mr.getDateReport().format(formatter));
        data.put("image", mr.getImage());
        data.put("status", mr.getStatus());

        return ResponseEntity.ok(data);
    }
}
