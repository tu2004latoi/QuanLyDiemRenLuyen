/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.services;

import com.dtt.pojo.ActivityRegistrations;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public interface ActivityRegistrationService {
    ActivityRegistrations addActivityRegistration(ActivityRegistrations ar);
    boolean isRegistration(int userId, int activityId);
    void registerToActivity(int activityId, int userId);
}
