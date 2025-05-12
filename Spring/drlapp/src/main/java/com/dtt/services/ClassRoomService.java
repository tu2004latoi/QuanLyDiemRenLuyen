/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.services;

import com.dtt.pojo.ClassRoom;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public interface ClassRoomService {
    List<ClassRoom> getAllClassRooms();
    List<ClassRoom> getClassesByFacultyId(int id);
    ClassRoom getClassRoomById(int id);
}
