/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.ClassRoom;
import com.dtt.repositories.ClassRoomRepository;
import com.dtt.services.ClassRoomService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class ClassRoomServiceImpl implements ClassRoomService{
    @Autowired
    private ClassRoomRepository classRepo;

    @Override
    public List<ClassRoom> getAllClassRooms() {
        return this.classRepo.getAllClassRooms();
    }

    @Override
    public List<ClassRoom> getClassesByFacultyId(int id) {
        return this.classRepo.getClassesByFacultyId(id);
    }

    @Override
    public ClassRoom getClassRoomById(int id) {
        return this.classRepo.getClassRoomById(id);
    }

    @Override
    public List<ClassRoom> getClasses(Map<String, String> params) {
        return this.classRepo.getClasses(params);
    }

    @Override
    public void deleteClassroom(ClassRoom c) {
        this.classRepo.deleteClassroom(c);
    }

    @Override
    public ClassRoom addOrUpdate(ClassRoom c) {
        return this.classRepo.addOrUpdate(c);
    }
    
}
