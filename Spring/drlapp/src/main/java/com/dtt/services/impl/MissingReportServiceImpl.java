/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dtt.pojo.MissingReport;
import com.dtt.repositories.MissingReportRepository;
import com.dtt.services.MissingReportService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public MissingReport addOrUpdateMissingReport(MissingReport mr) {
        if (mr.getFile() != null && !mr.getFile().isEmpty()){
            try{
                 Map res = cloudinary.uploader().upload(mr.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                mr.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ActivityServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.mrRepo.addOrUpdateMissingReport(mr);
    }

    @Override
    public List<MissingReport> getMissingReports(Map<String, String> params) {
        return this.mrRepo.getMissingReports(params);
    }

    @Override
    public MissingReport getMissingReportById(int id) {
        return this.mrRepo.getMissingReportById(id);
    }
    
}
