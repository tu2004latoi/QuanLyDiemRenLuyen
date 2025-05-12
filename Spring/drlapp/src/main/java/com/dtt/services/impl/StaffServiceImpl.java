/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Staff;
import com.dtt.repositories.StaffRepository;
import com.dtt.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class StaffServiceImpl implements StaffService{
    @Autowired
    private StaffRepository staffRepo;

    @Override
    public Staff getStaffByUserId(int id) {
        return this.staffRepo.getStaffByUserId(id);
    }

    @Override
    public Staff addOrUpdateStaff(Staff s) {
        return this.staffRepo.addOrUpdateStaff(s);
    }
    
}
