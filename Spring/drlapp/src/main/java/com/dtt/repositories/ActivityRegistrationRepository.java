/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.ActivityRegistrations;

/**
 *
 * @author MR TU
 */
public interface ActivityRegistrationRepository {
    ActivityRegistrations addActivityRegistration(ActivityRegistrations ar);
    boolean isRegistration(int userId, int activityId);
}
