/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Department;
import com.dtt.repositories.DepartmentRepository;
import com.dtt.services.DepartmentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    private DepartmentRepository departmentRepo;

    @Override
    public List<Department> getAllDepartments() {
        return this.departmentRepo.getAllDepartments();
    }

    @Override
    public Department getDepartmentById(int id) {
        return this.departmentRepo.getDepartmentById(id);
    }
    
}
