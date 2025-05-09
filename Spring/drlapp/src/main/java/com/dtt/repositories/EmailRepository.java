/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.Email;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MR TU
 */
@Repository
public interface EmailRepository {
    Email getEmailByEmail(String email);
    List<Email> getEmails(Map<String, String> params);
    void deleteEmail(Email e);
    Email addEmail(Email e);
    long getCountEmails();
}
