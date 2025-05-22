/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.ClassRoom;
import com.dtt.pojo.Notification;
import com.dtt.pojo.User;
import com.dtt.services.ClassRoomService;
import com.dtt.services.NotificationService;
import com.dtt.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author MR TU
 */
@ControllerAdvice
public class BaseController {

    @Autowired
    private UserService userSer;

    @Autowired
    private NotificationService noSer;

    @ModelAttribute
    public void addRequestUri(Model model, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        if (requestUri.startsWith(contextPath)) {
            requestUri = requestUri.substring(contextPath.length());
            if (requestUri.isEmpty()) {
                requestUri = "/";
            }
        }
        model.addAttribute("requestUri", requestUri);
    }

    @ModelAttribute("user")
    public User addUserInfoToModel() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return userSer.getUserByUsername(auth.getName());
        }
        return null;
    }

    @ModelAttribute("notifications")
    public List<Notification> addNotificationsToModel() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
                String username = auth.getName();
                User user = userSer.getUserByUsername(username);
                if (user != null) {
                    List<Notification> notifications = this.noSer.getNotificationsByUser(user);
                    // Optional: log size
                    System.out.println("Notifications count: " + notifications.size());
                    return notifications;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Log lỗi để dễ theo dõi
        }
        return List.of();
    }

    @ModelAttribute("unreadCount")
    public long countUnreadNotifications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            User user = userSer.getUserByUsername(auth.getName());
            return noSer.countUnreadByUser(user);
        }
        return 0;
    }

}
