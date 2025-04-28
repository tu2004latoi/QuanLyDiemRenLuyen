/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.User;

/**
 *
 * @author MR TU
 */

public interface UserRepository {
    User getUserByUsername(String username);
}
