/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dtt.pojo.Activity;
import com.dtt.repositories.ActivityRepository;
import com.dtt.services.ActivityService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepo;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<Activity> getAllActivities() {
        return this.activityRepo.getAllActivities();
    }

    @Override
    public Activity getActivityById(int id) {
        return this.activityRepo.getActivityById(id);
    }

    @Override
    public void deleteActivity(int id) {
        this.activityRepo.deleteActivity(id);
    }

    @Override
    public List<Activity> getActivities(Map<String, String> params) {
        return this.activityRepo.getActivities(params);
    }

    @Override
    public Activity addOrUpdateActivity(Activity a) {
        if (!a.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(a.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                a.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ActivityServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return this.activityRepo.addOrUpdateActivity(a);
    }
}
