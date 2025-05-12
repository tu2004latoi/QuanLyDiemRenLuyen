/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Student;
import com.dtt.pojo.User;
import com.dtt.repositories.StudentRepository;
import com.dtt.services.StudentService;
import com.dtt.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentRepository stRepo;
    
    @Autowired
    private UserService userSer;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Student addOrUpdateStudent(Student st) {
        if (st.getStudentId()==null || st.getStudentId().isEmpty()){
            st.setStudentId(generateStudentId());
        }
        return this.stRepo.addOrUpdateStudent(st);
    }
    
    @Override
    public Student getStudentByUserId(int id){
        return this.stRepo.getStudentByUserId(id);
    }
    
    public String generateStudentId() {
        // Lấy số lượng sinh viên hiện có
        long count = stRepo.count();

        // Tăng lên 1 để tạo mã mới
        long nextId = count + 1;

        // Format mã SV00001
        return String.format("SV%05d", nextId);
    } 
    
    @Override
    public long count(){
        return this.stRepo.count();
    }

    @Override
    public List<Student> getAllStudents() {
        return this.stRepo.getAllStudents();
    }

    @Override
    public void deleteStudentById(int id) {
        this.stRepo.deleteStudentById(id);
    }
    
    
}
