/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Email;
import com.dtt.repositories.EmailRepository;
import com.dtt.services.EmailService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    private EmailRepository emailRepo;

    @Override
    public Email getEmailByEmail(String email) {
        return this.emailRepo.getEmailByEmail(email);
    }

    @Override
    public List<Email> getEmails(Map<String, String> params) {
        return this.emailRepo.getEmails(params);
    }

    @Override
    public void deleteEmail(Email e) {
        this.emailRepo.deleteEmail(e);
    }

    @Override
    public Email addEmail(Email e) {
        return this.emailRepo.addEmail(e);
    }

    @Override
    public long getCountEmails() {
        return this.emailRepo.getCountEmails();
    }
    
}
