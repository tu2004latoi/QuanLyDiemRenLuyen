/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Notification;
import com.dtt.services.NotificationService;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api")
public class ApiNotificationController {
    @Autowired
    private NotificationService noSer;
    
    @GetMapping("/notifications")
    public ResponseEntity<?> notificationsView(){
        List<Notification> notifications = this.noSer.listNotifications();
        List<Map<String, Object>> listData = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        for (Notification n : notifications){
            Map<String, Object> data = new HashMap<>();
            data.put("id", n.getId());
            data.put("userId", n.getUser().getId());
            data.put("content", n.getContent());
            data.put("createAt", n.getCreatedAt().format(formatter));
            data.put("isRead", n.getIsRead());
            
            listData.add(data);
        }
        
        return ResponseEntity.ok(listData);
    }
    
}
