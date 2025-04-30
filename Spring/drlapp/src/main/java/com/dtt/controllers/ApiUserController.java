/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.User;
import com.dtt.services.UserService;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @PostMapping(path = "/users", consumes = MediaType.MULTIPART_FORM_DATA)
    public ResponseEntity<User> register(@RequestParam Map<String, String> params, 
            @RequestParam(value = "avatar") MultipartFile avatar) {
        User u = this.userSer.register(params, avatar);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }
    
    //Lấy toàn bộ Users
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userSer.getAllUsers();
    }
    
    //Chi tiết 1 User
    @GetMapping("users/{id}")
    public User getUserById(@PathVariable("id") int id){
        return userSer.getUserById(id);
    }
    
    //Xóa 1 User
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String destroy(@PathVariable("id") int id){
        this.userSer.deleteUserById(id);
        
        return "redirect:/users";
    }
    
}
