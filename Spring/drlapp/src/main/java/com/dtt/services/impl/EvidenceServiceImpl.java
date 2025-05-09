/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dtt.pojo.ActivityRegistrations;
import com.dtt.pojo.Evidence;
import com.dtt.repositories.EvidenceRepository;
import com.dtt.services.EvidenceService;
import java.io.IOException;
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
public class EvidenceServiceImpl implements EvidenceService{
    @Autowired
    private EvidenceRepository eviRepo;
    
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Evidence addOrUpdateEvidence(Evidence e) {
        if (e.getFile()!= null && !e.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(e.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                e.setFilePath(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ActivityServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.eviRepo.addOrUpdateEvidence(e);
    }

    @Override
    public void deleteEvidence(int id) {
        this.eviRepo.deleteEvidence(id);
    }

    @Override
    public Evidence getEvidenceByActivityRegistration(ActivityRegistrations ar) {
        return this.eviRepo.getEvidenceByActivityRegistration(ar);
    }

    @Override
    public Evidence getEvidenceById(int id) {
        return this.eviRepo.getEvidenceById(id);
    }

    @Override
    public Evidence getEvidenceByActivityRegistrationId(int id) {
        return this.eviRepo.getEvidenceByActivityRegistrationId(id);
    }

    @Override
    public Evidence getEvidenceByTrainingPointId(int id) {
        return this.eviRepo.getEvidenceByTrainingPointId(id);
    }
    
}
