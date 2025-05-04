/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.ActivityRegistrations;
import com.dtt.repositories.MyActivityRepository;
import com.dtt.services.MyActivityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class MyActivityServiceImpl implements MyActivityService{
    @Autowired
    private MyActivityRepository maRepo;

    @Override
    public List<ActivityRegistrations> getListMyActivities() {
        return this.maRepo.getListMyActivities();
    }

    @Override
    public void deleteMyActivityById(int id) {
        this.maRepo.deleteMyActivityById(id);
    }

    @Override
    public ActivityRegistrations getMyActivityById(int id) {
        return this.maRepo.getMyActivityById(id);
    }
    
}
