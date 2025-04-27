/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Activity;
import com.dtt.repositories.ActivityRepository;
import com.dtt.services.ActivityService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class ActivityServiceImpl implements ActivityService{
    @Autowired
    private ActivityRepository activityRepo;
    
    @Override
    public List<Activity> getAllActivities(){
        return this.activityRepo.getAllActivities();
    }

    @Override
    public Activity getActivityById(String id) {
        return this.activityRepo.getActivityById(id);
    }

    @Override
    public void deleteActivity(String id) {
        this.activityRepo.deleteActivity(id);
    }

    @Override
    public List<Activity> getActivities(Map<String, String> params) {
        return this.activityRepo.getActivities(params);
    }

    @Override
    public Activity addOrUpdateActivity(Activity a) {
        a.setImage("https://res.cloudinary.com/druxxfmia/image/upload/v1743651318/n0i4pxhiwbmqztwrdyjs.jpg");
        return this.activityRepo.addOrUpdateActivity(a);
    }
}
