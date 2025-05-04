/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.services;

import com.dtt.pojo.ActivityRegistrations;
import com.dtt.pojo.Evidence;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public interface EvidenceService {
    Evidence addOrUpdateEvidence(Evidence e);
    void deleteEvidence(int id);
    Evidence getEvidenceByActivityRegistration(ActivityRegistrations ar);
    Evidence getEvidenceById(int id);
    Evidence getEvidenceByActivityRegistrationId(int id);
}
