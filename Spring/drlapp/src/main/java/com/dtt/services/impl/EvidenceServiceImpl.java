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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
public class EvidenceServiceImpl implements EvidenceService {

    @Autowired
    private EvidenceRepository eviRepo;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Evidence addOrUpdateEvidence(Evidence e) {
        if (e.getFile() != null && !e.getFile().isEmpty()) {
            try {
                // 1. Chuyển MultipartFile thành File tạm để gửi sang Flask
                File tempFile = File.createTempFile("evidence", ".jpg");
                e.getFile().transferTo(tempFile);

                // 2. Kiểm tra chất lượng ảnh
                if (!checkImageQuality(tempFile)) {
                    tempFile.delete(); // xóa file tạm
                    throw new RuntimeException("Ảnh không hợp lệ: mờ, trắng hoặc đen!");
                }

                // 3. Upload lên Cloudinary
                Map res = cloudinary.uploader().upload(e.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                e.setFilePath(res.get("secure_url").toString());

                tempFile.delete(); // dọn file tạm

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

    @Override
    public boolean checkImageQuality(File imageFile) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofFile(imageFile.toPath());

            System.out.println("File size: " + imageFile.length());

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:5001/check"))
                    .header("Content-Type", "image/jpeg") // hoặc image/png tuỳ ảnh
                    .POST(body)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.body());

            boolean isBlurry = jsonNode.get("blurry").asBoolean();
            String color = jsonNode.get("color").asText();

            return !isBlurry && color.equals("normal");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
