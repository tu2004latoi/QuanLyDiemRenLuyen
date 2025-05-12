/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Student;
import com.dtt.repositories.StatisticsRepository;
import com.dtt.services.StatisticsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class StatisticsServiceImpl implements StatisticsService{
    @Autowired
    private StatisticsRepository sttRepo;

    @Override
    public List<Student> getStatistics(Map<String, String> params) {
        return this.sttRepo.getStatistics(params);
    }
    
}
