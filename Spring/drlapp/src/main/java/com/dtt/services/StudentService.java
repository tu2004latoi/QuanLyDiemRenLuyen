/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.services;

import com.dtt.pojo.Student;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public interface StudentService {
    Student addOrUpdateStudent(Student st);
    long count();
    Student getStudentByUserId(int id);
    List<Student> getAllStudents();
    void deleteStudentById(int id);
}
