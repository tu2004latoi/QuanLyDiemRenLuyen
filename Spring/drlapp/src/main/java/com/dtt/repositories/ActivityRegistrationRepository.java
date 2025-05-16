/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.ActivityRegistrations;
import java.util.List;

/**
 *
 * @author MR TU
 */
public interface ActivityRegistrationRepository {
    ActivityRegistrations addActivityRegistration(ActivityRegistrations ar);
    List<ActivityRegistrations> getAllActivityRegistrations();
    boolean isRegistration(int userId, int activityId);
    void deleteActivityRegistrationById(int id);
    ActivityRegistrations getActivityRegistrationById(int id);
    ActivityRegistrations getActivityRegistrationByUserIdAndActivityId(int userId, int activityId);
}
