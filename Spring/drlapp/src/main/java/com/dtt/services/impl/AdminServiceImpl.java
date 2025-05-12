/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Admin;
import com.dtt.repositories.AdminRepository;
import com.dtt.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminRepository adminRepo;

    @Override
    public Admin getAdminByUserId(int id) {
        return this.adminRepo.getAdminByUserId(id);
    }

    @Override
    public Admin addOrUpdateAdmin(Admin ad) {
        return this.adminRepo.addOrUpdateAdmin(ad);
    }
    
}
