/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Activity;
import static com.dtt.pojo.Activity.PointType.POINT_1;
import static com.dtt.pojo.Activity.PointType.POINT_2;
import static com.dtt.pojo.Activity.PointType.POINT_3;
import com.dtt.pojo.Evidence;
import com.dtt.pojo.TrainingPoint;
import com.dtt.pojo.User;
import com.dtt.services.EvidenceService;
import com.dtt.services.TrainingPointService;
import com.dtt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

}
