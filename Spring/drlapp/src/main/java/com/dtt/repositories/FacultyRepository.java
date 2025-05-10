/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.Faculty;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MR TU
 */
@Repository
public interface FacultyRepository {
    Faculty getFacultyById(int id);
    Faculty addOrUpdateFaculty(Faculty a);
    List<Faculty> getAllFaculties();
    void deleteFaculty(int id);
    Faculty getFacultyByName(String name);
}
