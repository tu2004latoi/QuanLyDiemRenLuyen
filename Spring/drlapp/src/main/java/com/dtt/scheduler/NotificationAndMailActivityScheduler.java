/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.scheduler;

import com.dtt.pojo.Activity;
import com.dtt.pojo.ActivityRegistrations;
import com.dtt.pojo.Notification;
import com.dtt.pojo.User;
import com.dtt.services.ActivityRegistrationService;
import com.dtt.services.ActivityService;
import com.dtt.services.EmailService;
import com.dtt.services.NotificationService;
import com.dtt.services.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author MR TU
 */
@Component
public class NotificationAndMailActivityScheduler {

    @Autowired
    private ActivityRegistrationService arSer;

    @Autowired
    private ActivityService activitySer;

    @Autowired
    private UserService userSer;

    @Autowired
    private EmailService emailSer;
    
    @Autowired
    private NotificationService noSer;

    @Scheduled(fixedRate = 60000)
    public void mailActivityToUser() {
        List<ActivityRegistrations> listActivityRegistrations = this.arSer.getAllActivityRegistrations();
        for (ActivityRegistrations ar : listActivityRegistrations) {
            if (!ar.isSendMail()) {
                LocalDate today = LocalDate.now();
                Date startDate = ar.getActivity().getStartDate();
                LocalDate activityStartDate = startDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                if (today.equals(activityStartDate)) {
                    User u = this.userSer.getUserById(ar.getUser().getId());
                    Activity a = this.activitySer.getActivityById(ar.getActivity().getId());
                    String subject = "Nhắc nhở tham gia hoạt động";
                    String content = "Xin chào " + u.getName() + ",\n\n"
                            + "Sắp đến thời gian diễn ra hoạt động: " + a.getName() + ".\n"
                            + "Thời gian: " + a.getStartDate() + " - " + a.getEndDate() + "\n"
                            + "Địa điểm: " + a.getLocation() + "\n"
                            + "Mời bạn đến tham gia " + "\n\n"
                            + "Trân trọng!";
                    System.out.println("Sending email to: " + u.getEmail());
                    this.emailSer.sendEmail(u.getEmail(), subject, content);
                    
                    Notification n = new Notification();
                    n.setUser(u);
                    n.setCreatedAt(LocalDateTime.now());
                    n.setContent("Chú ý tham gia hoạt động " + a.getName());
                    n.setIsRead(false);
                    this.noSer.addNotification(n);
                    
                    ar.setIsSendMail(true);
                    this.arSer.addActivityRegistration(ar);
                }
            }
        }
    }
}
