/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.User;
import com.dtt.services.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author MR TU
 */
@Controller
public class MessageController {
    @Autowired
    private UserService userSer;
    @GetMapping("/messages")
    public String messagesView(Model model, Principal principal){
        String username = principal.getName();
        User u = this.userSer.getUserByUsername(username);
        
        model.addAttribute("allUsers", this.userSer.getUsersExcept(username));
        model.addAttribute("username", u.getEmail());
        model.addAttribute("name", u.getName());
        return "messages";
    }
}
