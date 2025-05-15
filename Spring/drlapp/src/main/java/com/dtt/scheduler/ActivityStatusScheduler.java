/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.scheduler;

import com.dtt.pojo.Activity;
import com.dtt.services.ActivityService;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author MR TU
 */
@Component
public class ActivityStatusScheduler {
    @Autowired
    private ActivityService activitySer;
    
    @Scheduled(fixedRate = 60000)
    public void updateStatusActivity(){
        Date now = new Date();
        List<Activity> activities = this.activitySer.getAllActivities();
        for (Activity activity : activities) {
            Activity.ActivityStatus newStatus;

            if (activity.getStartDate() == null || activity.getEndDate() == null) {
                newStatus = Activity.ActivityStatus.UPCOMING;
            } else if (now.before(activity.getStartDate())) {
                newStatus = Activity.ActivityStatus.UPCOMING;
            } else if (!now.before(activity.getStartDate()) && !now.after(activity.getEndDate())) {
                newStatus = Activity.ActivityStatus.ONGOING;
            } else {
                newStatus = Activity.ActivityStatus.COMPLETED;
            }

            if (activity.getStatus() != newStatus) {
                activity.setStatus(newStatus);
                activitySer.addOrUpdateActivity(activity);
            }
        }
    }
    
}
