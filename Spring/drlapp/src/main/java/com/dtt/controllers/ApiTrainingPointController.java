/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.TrainingPoint;
import com.dtt.pojo.User;
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

    @DeleteMapping("/training-points/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String destroyTrainingPoint(@PathVariable("id") int id) {
        this.tpSer.deleteTrainingPointById(id);
        return "";
    }

    @PatchMapping("/training-points/confirm/{id}")
    public ResponseEntity<?> confirmTrainingPoint(@PathVariable("id") int id) {
        TrainingPoint t = this.tpSer.getTrainingPointById(id);
        if (t == null) {
            return new ResponseEntity<>("Training point not found", HttpStatus.NOT_FOUND);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User u = this.userSer.getUserByUsername(username);
        if (u == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        t.setConfirmedBy(u);
        t.setStatus(TrainingPoint.Status.CONFIRMED);

        try {
            this.tpSer.addOrUpdateTrainingPoint(t);
            return ResponseEntity.ok("Successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating training point: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/training-points/reject/{id}")
    public ResponseEntity<?> rejectTrainingPoint(@PathVariable("id") int id) {
        TrainingPoint t = this.tpSer.getTrainingPointById(id);
        if (t == null) {
            return new ResponseEntity<>("Training point not found", HttpStatus.NOT_FOUND);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User u = this.userSer.getUserByUsername(username);
        if (u == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        t.setConfirmedBy(u);
        t.setStatus(TrainingPoint.Status.REJECTED);

        try {
            this.tpSer.addOrUpdateTrainingPoint(t);
            return ResponseEntity.ok("Successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating training point: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
