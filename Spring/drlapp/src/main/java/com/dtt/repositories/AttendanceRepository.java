/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.Attendance;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MR TU
 */
@Repository
public interface AttendanceRepository {
    Attendance addAttendance(Attendance at);
    List<Attendance> getAllAttendance();
    void deleteAttendance(Attendance at);
    Attendance getAttendanceById(int id);
}
