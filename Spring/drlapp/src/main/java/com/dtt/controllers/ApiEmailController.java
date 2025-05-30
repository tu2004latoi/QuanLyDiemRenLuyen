/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Email;
import com.dtt.pojo.User;
import com.dtt.services.EmailService;
import com.dtt.services.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api")
public class ApiEmailController {

    @Autowired
    private EmailService emailSer;

    @Autowired
    private UserService userSer;

    @DeleteMapping("/emails/{e}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmail(@PathVariable("e") String e, Principal principal) {
        User u = userSer.getUserByUsername(principal.getName());
        if (u.getRole() == User.Role.STUDENT) {
            throw new AccessDeniedException("Sinh viên không được phép xóa email");
        }
        Email email = this.emailSer.getEmailByEmail(e);
        this.emailSer.deleteEmail(email);
    }

    @GetMapping("/public/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam("email") String email) {
        // Kiểm tra email có tồn tại trong DB hay không
        Email emailRecord = emailSer.getEmailByEmail(email);
        if (emailRecord == null) {
            // Email không tồn tại => hợp lệ
            return ResponseEntity.ok("Email chưa được đăng ký");
        }

        // Email tồn tại, kiểm tra đã liên kết user chưa
        User linkedUser = userSer.getUserByEmail(email);
        if (linkedUser != null) {
            // Email đã liên kết user => trả lỗi 409
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email đã được sử dụng để đăng ký tài khoản");
        }

        // Email tồn tại nhưng chưa liên kết user (có thể thông báo khác hoặc cho phép đăng ký)
        return ResponseEntity.status(HttpStatus.OK).body("Email hợp lệ");
    }
}
