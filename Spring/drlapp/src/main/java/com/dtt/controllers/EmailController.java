/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Email;
import com.dtt.services.EmailService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author MR TU
 */
@Controller
public class EmailController {
    @Autowired
    private EmailService emailSer;
    
    @GetMapping("/emails")
    public String emailListView(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("emails", this.emailSer.getEmails(params));
        
        return "listEmails";
    }
    
    @GetMapping("/emails/add")
    public String addEmailView(Model model){
        model.addAttribute("email", new Email());
        
        return "addEmail";
    }
    
    @PostMapping("/emails/add")
    public String addEmail(@ModelAttribute("email") @Valid Email e, Model model){
        this.emailSer.addEmail(e);
        
        return "redirect:/emails";
    }
    
}
