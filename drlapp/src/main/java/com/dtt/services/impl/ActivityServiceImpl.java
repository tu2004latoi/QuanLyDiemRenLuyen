/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Activity;
import com.dtt.repositories.ActivityRepository;
import com.dtt.services.ActivityService;
import java.util.List;
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
}
