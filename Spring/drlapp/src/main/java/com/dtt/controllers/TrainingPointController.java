/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Activity;
import com.dtt.pojo.ActivityRegistrations;
import com.dtt.pojo.Evidence;
import com.dtt.pojo.TrainingPoint;
import com.dtt.pojo.User;
import com.dtt.services.ActivityRegistrationService;
import com.dtt.services.ActivityService;
import com.dtt.services.EvidenceService;
import com.dtt.services.TrainingPointService;
import com.dtt.services.UserService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author MR TU
 */
@Controller
public class TrainingPointController {

    @Autowired
    private TrainingPointService tpSer;

    @Autowired
    private UserService userSer;

    @Autowired
    private ActivityService activitySer;

    @Autowired
    private EvidenceService evidenceSer;

    @Autowired
    private ActivityRegistrationService arSer;

    @GetMapping("/training-points")
    public String trainingPointListView(Model model, @RequestParam Map<String, String> params) {
        List<TrainingPoint> listTrainingPoints = this.tpSer.getTrainingPoints(params);
        Map<Integer, Evidence> evidenceMap = new HashMap<>();
        for (TrainingPoint t : listTrainingPoints) {
            int trainingPointId = t.getId();
            Evidence e = this.evidenceSer.getEvidenceByTrainingPointId(trainingPointId);
            evidenceMap.put(trainingPointId, e);
        }
        
        model.addAttribute("tp", listTrainingPoints);
        model.addAttribute("evidenceMap", evidenceMap);

        return "trainingPointsList";
    }

//    @DeleteMapping("/training-points/update-evidence/{id}")
//    public String deleteEvidence(@RequestParam("id") Integer arId){
//        
//    }
}
