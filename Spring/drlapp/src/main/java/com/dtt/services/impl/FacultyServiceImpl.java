/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Faculty;
import com.dtt.repositories.FacultyRepository;
import com.dtt.services.FacultyService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class FacultyServiceImpl implements FacultyService{
    @Autowired
    private FacultyRepository facultyRepo;

    @Override
    public Faculty getFacultyById(int id) {
        return this.facultyRepo.getFacultyById(id);
    }

    @Override
    public Faculty addOrUpdateFaculty(Faculty a) {
        return this.facultyRepo.addOrUpdateFaculty(a);
    }

    @Override
    public List<Faculty> getAllFaculties() {
        return this.facultyRepo.getAllFaculties();
    }

    @Override
    public void deleteFaculty(int id) {
        this.facultyRepo.deleteFaculty(id);
    }

    @Override
    public Faculty getFacultyByName(String name) {
        return this.facultyRepo.getFacultyByName(name);
    }

    @Override
    public List<Faculty> getFaculties(Map<String, String> params) {
        return this.facultyRepo.getFaculties(params);
    }
    
}
