/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.services;

import com.dtt.pojo.ActivityRegistrations;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public interface MyActivityService {
    List<ActivityRegistrations> getListMyActivities();
    void deleteMyActivityById(int id);
    ActivityRegistrations getMyActivityById(int id);
}
