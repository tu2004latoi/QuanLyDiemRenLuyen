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
import com.dtt.services.MyActivityService;
import com.dtt.services.TrainingPointService;
import com.dtt.services.UserService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author MR TU
 */
@Controller
public class MyActivityController {

    @Autowired
    private MyActivityService maSer;

    @Autowired
    private EvidenceService evidenceSer;
    
    @GetMapping("/my-activities")
    public String listMyActivitiesView(Model model) {
        List<ActivityRegistrations> myActivities = this.maSer.getListMyActivities();
        Map<Integer, Evidence> evidenceMap = new HashMap<>();
        for (ActivityRegistrations ar : myActivities) {
//            int userId = ar.getUser().getId();
//            int activityId = ar.getActivity().getId();
            try {
                Evidence e = this.evidenceSer.getEvidenceByActivityRegistration(ar);
                if (e != null) {
                    evidenceMap.put(ar.getId(), e);
                }
            } catch (Exception e) {
                evidenceMap.put(ar.getId(), null); // nếu không có, set null
            }
        }
        model.addAttribute("myActivity", myActivities);
        model.addAttribute("evidenceMap", evidenceMap);

        return "myActivity";
    }
     
}
