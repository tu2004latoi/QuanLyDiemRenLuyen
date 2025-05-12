/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.services;

import com.dtt.pojo.Activity;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public interface ActivityService {
    Activity getActivityById(int id);
    List<Activity> getAllActivities();
    void deleteActivity(int id);
    List<Activity> getActivities(Map<String, String> params);
    Activity addOrUpdateActivity(Activity a);
    long getCountActivities();
    long count(Map<String, String> params);
}
