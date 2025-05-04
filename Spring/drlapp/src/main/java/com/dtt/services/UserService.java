/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.services;

import com.dtt.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author MR TU
 */
public interface UserService extends UserDetailsService{
    User getUserByUsername(String username);
    User register(Map<String, String> params, MultipartFile avatar);
    User getUserById(int id);
    User addOrUpdateUser(User u);
    List<User> getUsers(Map<String, String> params);
    List<User> getAllUsers();
    void deleteUserById(int id);
    long getCountUsers();
}
