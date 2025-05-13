/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.User;
import com.dtt.services.ActivityRegistrationService;
import com.dtt.services.UserService;
import com.dtt.utils.JwtUtils;
import jakarta.ws.rs.core.MediaType;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author kieum
 */
@RestController
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    private UserService userSer;

    @Autowired
    private ActivityRegistrationService arSer;

    @PostMapping(path = "/users", consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity<User> register(@RequestParam Map<String, String> params,
            @RequestParam(value = "avatar") MultipartFile avatar) {
        User u = this.userSer.register(params, avatar);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    //Lấy toàn bộ Users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userSer.getAllUsers();
    }

    //Chi tiết 1 User
    @GetMapping("users/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userSer.getUserById(id);
    }

    //Xóa 1 User
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String destroyUser(@PathVariable("id") int id) {
        this.userSer.deleteUserById(id);

        return "redirect:/users";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User u) {
        if (u.getEmail() == null || u.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username hoặc password không được để trống");
        }

        if (this.userSer.authenticate(u.getEmail(), u.getPassword())) {
            try {
                String token = JwtUtils.generateToken(u.getEmail());
                return ResponseEntity.ok().body(Collections.singletonMap("token", token));
            } catch (Exception e) {
                return ResponseEntity.status(500).body("Lỗi khi tạo JWT");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sai thông tin đăng nhập");
    }

    @RequestMapping("/secure/profile")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<User> getProfile(Principal principal) {
        return new ResponseEntity<>(this.userSer.getUserByUsername(principal.getName()), HttpStatus.OK);
    }

}
