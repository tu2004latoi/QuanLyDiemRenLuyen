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
    Activity getActivityById(String id);
    List<Activity> getActivities(Map<String, String> params);
    Activity addOrUpdateActivity(Activity a);
    List<Activity> getAllActivities();
    void deleteActivity(String id);
//    List<Activity> getActivitiesByStatus(String status);
}
