/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Notification;
import com.dtt.pojo.User;
import com.dtt.repositories.NotificationRepository;
import com.dtt.services.NotificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private NotificationRepository notiRepo;

    @Override
    public List<Notification> listNotifications() {
        return this.notiRepo.listNotifications();
    }

    @Override
    public Notification getNotificationById(int id) {
        return this.notiRepo.getNotificationById(id);
    }

    @Override
    public List<Notification> getNotificationsByUser(User u) {
        return this.notiRepo.getNotificationsByUser(u);
    }

    @Override
    public void deleteNotification(Notification n) {
        this.notiRepo.deleteNotification(n);
    }

    @Override
    public Long size() {
        return this.notiRepo.size();
    }

    @Override
    public Notification addNotification(Notification n) {
        return this.notiRepo.addNotification(n);
    }

    @Override
    public List<Notification> listNotReadByUser(User u) {
        return this.notiRepo.listNotReadByUser(u);
    }

    @Override
    public void markAsRead(Notification n) {
        this.notiRepo.markAsRead(n);
    }

    @Override
    public void markAllAsRead(User u) {
        this.notiRepo.markAllAsRead(u);
    }

    @Override
    public long countUnreadByUser(User u) {
        return this.notiRepo.countUnreadByUser(u);
    }
    
}
