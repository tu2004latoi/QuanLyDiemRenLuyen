/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MR TU
 */
@Repository
public interface UserRepository {
    User getUserByUsername(String username);
    User getUserById(int id);
    User register(User u);
    User addOrUpdateUser(User u);
    void deleteUserById(int id);
    List<User> getAllUsers();
    List<User> getUsers(Map<String, String> params);
    long getCountUsers();
    User updatePointUser(User u);
    List<User> getAllStudents();
    boolean authenticate(String email, String password);
}
