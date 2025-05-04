/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Activity;
import com.dtt.pojo.ActivityRegistrations;
import com.dtt.pojo.TrainingPoint;
import com.dtt.pojo.User;
import com.dtt.repositories.ActivityRegistrationRepository;
import com.dtt.repositories.ActivityRepository;
import com.dtt.repositories.TrainingPointRepository;
import com.dtt.repositories.UserRepository;
import com.dtt.services.ActivityRegistrationService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class ActivityRegistrationServiceImpl implements ActivityRegistrationService {

    @Autowired
    private ActivityRegistrationRepository arRepo;

    @Autowired
    private ActivityRepository activityRepo;

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private TrainingPointRepository tpRepo;

    @Override
    public ActivityRegistrations addActivityRegistration(ActivityRegistrations ar) {
        return this.arRepo.addActivityRegistration(ar);
    }

    @Override
    public boolean isRegistration(int userId, int activityId) {
        return this.arRepo.isRegistration(userId, activityId);
    }

    @Override
    public void registerToActivity(int userId, int activityId) {
        Activity a = activityRepo.getActivityById(activityId);
        User u = userRepo.getUserById(userId);
        if (a == null) {
            throw new RuntimeException("Activity not found");
        }

        if (a.getMaxParticipants() != null
                && a.getCurrentParticipants() >= a.getMaxParticipants()) {
            throw new RuntimeException("Đã đủ số lượng người tham gia");
        }

        if (!arRepo.isRegistration(userId, activityId)) {
            throw new RuntimeException("Bạn đã đăng ký hoạt động này");
        }

        ActivityRegistrations ar = new ActivityRegistrations();
        ar.setUser(u);
        ar.setActivity(a);
        ar.setRegistrationDate(LocalDateTime.now());

        arRepo.addActivityRegistration(ar);
        
//        TrainingPoint t = new TrainingPoint();
//        t.setUser(u);
//        t.setActivity(a);
//        t.setPoint(a.getPointValue());
//        t.setDateAwarded(LocalDateTime.now());
//        t.setStatus(TrainingPoint.Status.PENDING);
//        t.setConfirmedBy(null);
//        
//        tpRepo.addOrUpdateTrainingPoint(t);

        // Tăng số người tham gia và cập nhật
        a.setCurrentParticipants(a.getCurrentParticipants() + 1);
        activityRepo.addOrUpdateActivity(a);
    }

    @Override
    public List<ActivityRegistrations> getAllActivityRegistrations() {
        return this.arRepo.getAllActivityRegistrations();
    }

    @Override
    public ActivityRegistrations getActivityRegistrationById(int id) {
        return this.arRepo.getActivityRegistrationById(id);
    }

    @Override
    public void deleteActivityRegistrationById(int id) {
        this.arRepo.deleteActivityRegistrationById(id);
    }

}
