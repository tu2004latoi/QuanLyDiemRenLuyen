/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Attendance;
import com.dtt.repositories.AttendanceRepository;
import com.dtt.services.AttendanceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class AttendanceServiceImpl implements AttendanceService{
    @Autowired
    private AttendanceRepository atRepo;

    @Override
    public Attendance addAttendance(Attendance at) {
        return this.atRepo.addAttendance(at);
    }

    @Override
    public List<Attendance> getAllAttendance() {
        return this.atRepo.getAllAttendance();
    }

    @Override
    public void deleteAttendance(Attendance at) {
        this.atRepo.deleteAttendance(at);
    }

    @Override
    public Attendance getAttendanceById(int id) {
        return this.atRepo.getAttendanceById(id);
    }
    
}
