/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.ActivityRegistrations;
import com.dtt.pojo.Evidence;
import com.dtt.pojo.User;
import com.dtt.services.EvidenceService;
import com.dtt.services.MyActivityService;
import com.dtt.services.UserService;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api")
public class ApiMyActivityController {

    @Autowired
    private MyActivityService maSer;

    @Autowired
    private EvidenceService evidenceSer;

    @Autowired
    private UserService userSer;

    @GetMapping("/my-activities")
    public ResponseEntity<?> listMyActivitiesApi() {
        try {
            List<ActivityRegistrations> myActivities = maSer.getListMyActivities();

            // Tạo danh sách các kết quả với id và các trường cần thiết
            List<Map<String, Object>> activitiesList = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            for (ActivityRegistrations ar : myActivities) {
                Map<String, Object> activityData = new HashMap<>();

                // Thêm id của ActivityRegistration
                activityData.put("id", ar.getId()); // id của ActivityRegistration

                // Thêm thông tin người dùng và hoạt động
                activityData.put("userId", ar.getUser().getId()); // tên người dùng
                activityData.put("activityId", ar.getActivity().getId()); // tên hoạt động
                activityData.put("registrationDate", ar.getRegistrationDate().format(formatter));

                // Lấy evidence cho mỗi ActivityRegistration
                Evidence e = null;
                try {
                    e = evidenceSer.getEvidenceByActivityRegistration(ar);
                } catch (Exception ex) {
                    // Nếu không tìm được evidence, giữ null
                }

                // Thêm evidenceId và trainingPointId (nếu có)
                activityData.put("evidenceId", e != null ? e.getId() : null);
                activityData.put("trainingPointId", e != null ? e.getTrainingPoint() != null ? e.getTrainingPoint().getId() : null : null);

                // Thêm phần còn lại nếu cần thiết, như evidence verifyStatus hay file path
                activityData.put("verifyStatus", e != null ? e.getVerifyStatus() : null);
                activityData.put("filePath", e != null && e.getFilePath() != null ? e.getFilePath() : null);

                // Thêm activityData vào danh sách kết quả
                activitiesList.add(activityData);
            }

            // Trả về kết quả dưới dạng danh sách
            return ResponseEntity.ok(activitiesList);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi lấy danh sách hoạt động: " + ex.getMessage());
        }
    }

    @GetMapping("/my-activities/{id}")
    public ResponseEntity<?> getDetails(@PathVariable("id") int id) {
        ActivityRegistrations ar = this.maSer.getMyActivityById(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Map<String, Object> data = Map.of(
                "id", ar.getId(),
                "userId", ar.getUser().getId(),
                "activityId", ar.getActivity().getId(),
                "registrationDate", ar.getRegistrationDate().format(formatter)
        );

        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/my-activities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyMyActivity(@PathVariable("id") int id) {
        this.maSer.deleteMyActivityById(id);
    }
}
