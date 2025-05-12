/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.Activity;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author admin
 */
@Repository
public interface ActivityRepository {
    Activity getActivityById(int id);
    List<Activity> getActivities(Map<String, String> params);
    Activity addOrUpdateActivity(Activity a);
    List<Activity> getAllActivities();
    void deleteActivity(int id);
    long getCountActivities();
    long count(Map<String, String> params);
//    List<Activity> getActivitiesByStatus(String status);
}
