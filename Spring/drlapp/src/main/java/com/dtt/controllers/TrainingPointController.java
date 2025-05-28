/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    
    @Autowired
    private Cloudinary cloudinary;

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

    @PostMapping("/training-points/create")
    public String createTrainingPoint(
            @RequestParam("arId") Integer arId,
            @RequestParam("userId") Integer userId,
            @RequestParam("activityId") Integer activityId,
            @RequestParam("point") Integer point,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        try {
            // Tạo file tạm từ MultipartFile
            File tempFile = File.createTempFile("upload-", ".tmp");
            file.transferTo(tempFile);

            // Kiểm tra chất lượng ảnh
            boolean isGood = this.evidenceSer.checkImageQuality(tempFile);
            if (!isGood) {
                tempFile.delete();
                redirectAttributes.addFlashAttribute("error", "Ảnh bạn tải lên bị mờ, trắng hoặc đen. Vui lòng thử lại!");
                return "redirect:/my-activities";
            }

            Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());
            String imageUrl = uploadResult.get("secure_url").toString();

            // Xoá file tạm sau khi upload
            tempFile.delete();

            // Tạo đối tượng entity
            ActivityRegistrations ar = this.arSer.getActivityRegistrationById(arId);
            User u = this.userSer.getUserById(userId);
            Activity a = this.activitySer.getActivityById(activityId);

            TrainingPoint t = new TrainingPoint();
            t.setUser(u);
            t.setActivity(a);
            t.setPoint(point);
            t.setDateAwarded(LocalDateTime.now());
            t.setConfirmedBy(null);
            t.setStatus(TrainingPoint.Status.PENDING);
            this.tpSer.addOrUpdateTrainingPoint(t);

            Evidence e = new Evidence();
            e.setActivityRegistration(ar);
            e.setUser(u);
            e.setTrainingPoint(t);
            e.setFilePath(imageUrl); // dùng URL từ Cloudinary
            e.setUploadDate(LocalDateTime.now());
            e.setVerifyStatus(Evidence.VerifyStatus.PENDING);
            this.evidenceSer.addOrUpdateEvidence(e);

            redirectAttributes.addFlashAttribute("msg", "Gửi minh chứng thành công!");

        } catch (Exception ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi xử lý ảnh.");
        }

        return "redirect:/my-activities";
    }

//    @DeleteMapping("/training-points/update-evidence/{id}")
//    public String deleteEvidence(@RequestParam("id") Integer arId){
//        
//    }
}
