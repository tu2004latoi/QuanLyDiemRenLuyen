/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.Student;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MR TU
 */
@Repository
public interface StudentRepository {
    Student addOrUpdateStudent(Student st);
    long count();
    Student getStudentByUserId(int id);
    List<Student> getAllStudents();
    void deleteStudentById(int id);
}
