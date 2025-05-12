/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.ClassRoom;
import com.dtt.services.ClassRoomService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api")
public class ApiClassRoomController {

    @Autowired
    private ClassRoomService classRoomSer;

    @GetMapping("/users/faculty/{facultyId}/classes")
    public List<ClassRoom> getClassesByFaculty(@PathVariable("facultyId") int facultyId) {
        return classRoomSer.getClassesByFacultyId(facultyId);
    }
}
