/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.Admin;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MR TU
 */
@Repository
public interface AdminRepository {
    Admin getAdminByUserId(int id);
    Admin addOrUpdateAdmin(Admin ad);
}
