/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.Notification;
import com.dtt.pojo.User;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MR TU
 */
@Repository
public interface NotificationRepository {
    Notification addNotification(Notification n);
    List<Notification> listNotReadByUser(User u);
    void markAsRead(Notification n);
    void markAllAsRead(User u);
    long countUnreadByUser(User u);
    List<Notification> listNotifications();
    Notification getNotificationById(int id);
    List<Notification> getNotificationsByUser(User u);
    Long size();
    void deleteNotification(Notification n);
}
