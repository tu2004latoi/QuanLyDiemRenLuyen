/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Activity;
import com.dtt.pojo.TrainingPoint;
import com.dtt.pojo.User;
import com.dtt.repositories.ActivityRepository;
import com.dtt.repositories.TrainingPointRepository;
import com.dtt.repositories.UserRepository;
import com.dtt.services.TrainingPointService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class TrainingPointServiceImpl implements TrainingPointService{
    @Autowired
    private TrainingPointRepository trainingPointRepo;
    
    @Autowired
    private ActivityRepository activityRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public void registerToTrainingPoint(int userId, int activityId) {
        Activity a = activityRepo.getActivityById(activityId);
        User u = userRepo.getUserById(userId);
        
        TrainingPoint t = new TrainingPoint();
        t.setActivity(a);
        t.setUser(u);
        t.setPoint(a.getPointValue());
        t.setDateAwarded(LocalDateTime.now());
        t.setConfirmedBy(null);
        t.setStatus(TrainingPoint.Status.PENDING);
        
        trainingPointRepo.addOrUpdateTrainingPoint(t);
    }

    @Override
    public TrainingPoint addOrUpdateTrainingPoint(TrainingPoint t) {
        return this.trainingPointRepo.addOrUpdateTrainingPoint(t);
    }

    @Override
    public List<TrainingPoint> getAllTrainingPoints() {
        return this.trainingPointRepo.getAllTrainingPoints();
    }

    @Override
    public TrainingPoint getTrainingPointById(int id) {
        return this.trainingPointRepo.getTrainingPointById(id);
    }

    @Override
    public void deleteTrainingPointById(int id) {
        this.trainingPointRepo.deleteTrainingPointById(id);
    }

    @Override
    public List<TrainingPoint> getTrainingPoints(Map<String, String> params) {
        return this.trainingPointRepo.getTrainingPoints(params);
    }

    @Override
    public TrainingPoint getTrainingPointByUserIdAndActivityId(int userId, int activityId) {
        return this.trainingPointRepo.getTrainingPointByUserIdAndActivityId(userId, activityId);
    }
    
}
