/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.services;

import com.dtt.pojo.Attendance;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public interface AttendanceService {
    Attendance addAttendance(Attendance at);
    List<Attendance> getAllAttendance();
    void deleteAttendance(Attendance at);
    Attendance getAttendanceById(int id);
}
