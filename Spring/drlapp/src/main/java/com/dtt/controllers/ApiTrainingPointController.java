/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Activity;
import static com.dtt.pojo.Activity.PointType.POINT_1;
import static com.dtt.pojo.Activity.PointType.POINT_2;
import static com.dtt.pojo.Activity.PointType.POINT_3;
import com.dtt.pojo.ActivityRegistrations;
import com.dtt.pojo.Evidence;
import com.dtt.pojo.TrainingPoint;
import com.dtt.pojo.User;
import com.dtt.services.ActivityRegistrationService;
import com.dtt.services.ActivityService;
import com.dtt.services.EvidenceService;
import com.dtt.services.TrainingPointService;
import com.dtt.services.UserService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api")
public class ApiTrainingPointController {

    @Autowired
    private TrainingPointService tpSer;

    @Autowired
    private UserService userSer;

    @Autowired
    private EvidenceService evidenceSer;
    
    @Autowired
    private ActivityRegistrationService arSer;
    
    @Autowired
    private ActivityService actSer;

    @GetMapping("/training-points")
    public ResponseEntity<?> getAllTrainingPoint() {
        List<TrainingPoint> listTrainingPoints = this.tpSer.getAllTrainingPoints();
        List<Map<String, Object>> listData = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (TrainingPoint tp : listTrainingPoints) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", tp.getId());
            data.put("userId", tp.getUser().getId());
            data.put("userName", tp.getUser().getName());
            data.put("userEmail", tp.getUser().getEmail());
            data.put("activityId", tp.getActivity().getId());
            data.put("activityName", tp.getActivity().getName());
            data.put("pointType", tp.getActivity().getPointType().getLabel());
            data.put("point", tp.getPoint());
            data.put("evidence", tp.getEvidences().getFilePath());
            data.put("dataAwarded", tp.getDateAwarded().format(formatter));
            data.put("status", tp.getStatus());
            data.put("comfirmBy", tp.getConfirmedBy() == null ? "Chưa xác nhận" : tp.getConfirmedBy().getName());

            listData.add(data);
        }

        return ResponseEntity.ok(listData);
    }

    @GetMapping("training-points/{id}")
    public ResponseEntity<?> getTrainingPoint(@PathVariable("id") int id) {
        TrainingPoint tp = this.tpSer.getTrainingPointById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> data = new HashMap<>();
        data.put("id", tp.getId());
        data.put("userId", tp.getUser().getId());
        data.put("userName", tp.getUser().getName());
        data.put("userEmail", tp.getUser().getEmail());
        data.put("activityId", tp.getActivity().getId());
        data.put("activityName", tp.getActivity().getName());
        data.put("pointType", tp.getActivity().getPointType().getLabel());
        data.put("point", tp.getPoint());
        data.put("evidence", tp.getEvidences().getFilePath());
        data.put("dataAwarded", tp.getDateAwarded().format(formatter));
        data.put("status", tp.getStatus());
        data.put("comfirmBy", tp.getConfirmedBy() == null ? "Chưa xác nhận" : tp.getConfirmedBy().getName());

        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/training-points/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String destroyTrainingPoint(@PathVariable("id") int id) {
        this.tpSer.deleteTrainingPointById(id);
        return "";
    }

    @PatchMapping("/training-points/confirm/{id}")
    public ResponseEntity<?> confirmTrainingPoint(@PathVariable("id") int id) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User u = this.userSer.getUserByUsername(username);
        if (u == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        this.tpSer.confirmTrainingPointById(id, u);

        return ResponseEntity.ok("Successfully");
    }

    @PatchMapping("/training-points/reject/{id}")
    public ResponseEntity<?> rejectTrainingPoint(@PathVariable("id") int id) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User u = this.userSer.getUserByUsername(username);
        if (u == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        this.tpSer.rejectTrainingPointById(id, u);

        return ResponseEntity.ok("Successfully");
    }

    @PatchMapping("/training-points/reject-after-approved/{id}")
    public ResponseEntity<?> rejectAfterApproved(@PathVariable("id") int id) {
        TrainingPoint t = this.tpSer.getTrainingPointById(id);
        if (t == null) {
            return new ResponseEntity<>("Training point not found", HttpStatus.NOT_FOUND);
        }

        t.setStatus(TrainingPoint.Status.REJECTED);

        Evidence e = this.evidenceSer.getEvidenceByTrainingPointId(id);
        e.setVerifyStatus(Evidence.VerifyStatus.REJECTED);

        Activity.PointType type = t.getActivity().getPointType();
        User addPointUser = t.getUser();
        int point = t.getPoint();
        switch (type) {
            case POINT_1 ->
                addPointUser.setPoint_1(addPointUser.getPoint_1() - point);
            case POINT_2 ->
                addPointUser.setPoint_2(addPointUser.getPoint_2() - point);
            case POINT_3 ->
                addPointUser.setPoint_3(addPointUser.getPoint_3() - point);
            default ->
                addPointUser.setPoint_4(addPointUser.getPoint_4() - point);
        }

        try {
            this.tpSer.addOrUpdateTrainingPoint(t);
            this.evidenceSer.addOrUpdateEvidence(e);
            this.userSer.updatePointUser(addPointUser);

            return ResponseEntity.ok("Successfully");
        } catch (Exception ex) {
            return new ResponseEntity<>("Error updating training point: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/training-points/create")
    public ResponseEntity<?> createTrainingPoint(
            @RequestParam("arId") Integer arId,
            @RequestParam("userId") Integer userId,
            @RequestParam("activityId") Integer activityId,
            @RequestParam("point") Integer point,
            @RequestParam("file") MultipartFile file) {

        try {
            ActivityRegistrations ar = arSer.getActivityRegistrationById(arId);
            User u = userSer.getUserById(userId);
            Activity a = actSer.getActivityById(activityId);

            TrainingPoint t = new TrainingPoint();
            t.setUser(u);
            t.setActivity(a);
            t.setPoint(point);
            t.setDateAwarded(LocalDateTime.now());
            t.setConfirmedBy(null);
            t.setStatus(TrainingPoint.Status.PENDING);

            tpSer.addOrUpdateTrainingPoint(t);

            Evidence e = new Evidence();
            e.setActivityRegistration(ar);
            e.setUser(u);
            e.setTrainingPoint(t);
            e.setFile(file);
            e.setUploadDate(LocalDateTime.now());
            e.setVerifyStatus(Evidence.VerifyStatus.PENDING);

            evidenceSer.addOrUpdateEvidence(e);

            return ResponseEntity.ok("Training point and evidence created successfully.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + ex.getMessage());
        }
    }

}
