/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.User;
import com.dtt.services.NotificationService;
import com.dtt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author MR TU
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notiSer;

    @Autowired
    private UserService userSer;

    @GetMapping("/notifications/mark-all-read")
    public String markAllAsRead(@RequestParam(name = "returnUrl", required = false) String returnUrl) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            User user = userSer.getUserByUsername(auth.getName());
            notiSer.markAllAsRead(user);
        }

        if (returnUrl != null && !returnUrl.isBlank()) {
            return "redirect:" + returnUrl;
        }

        return "redirect:/";
    }

}

