/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.services;

import com.dtt.pojo.Email;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public interface EmailService {
    Email getEmailByEmail(String email);
    List<Email> getEmails(Map<String, String> params);
    void deleteEmail(Email e);
    Email addEmail(Email e);
    long getCountEmails();
}
