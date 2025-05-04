/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.MissingReport;
import com.dtt.repositories.MissingReportRepository;
import com.dtt.services.MissingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class MissingReportServiceImpl implements MissingReportService{
    @Autowired
    private MissingReportRepository mrRepo;

    @Override
    public MissingReport addOrUpdateMissingReport(MissingReport mr) {
        return this.mrRepo.addOrUpdateMissingReport(mr);
    }
    
}
