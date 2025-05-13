/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.ClassRoom;
import com.dtt.pojo.Faculty;
import com.dtt.services.ClassRoomService;
import com.dtt.services.FacultyService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author MR TU
 */
@Controller
public class ClassroomController {
    @Autowired
    private ClassRoomService classSer;
    
    @Autowired
    private FacultyService facSer;
    
    @GetMapping("/classes")
    private String classesView(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("classes", this.classSer.getClasses(params));
        model.addAttribute("faculties", this.facSer.getAllFaculties());
        
        return "classes";
    }
    
    @GetMapping("/classes/add")
    private String manageClassroom(Model model){
        ClassRoom c = new ClassRoom();
        model.addAttribute("faculties", this.facSer.getAllFaculties());
        model.addAttribute("classroom", c);
        
        return "addClassroom";
    }
    
    @PostMapping("/classes/add")
    private String addClassroom(@ModelAttribute("classroom") @Valid ClassRoom c,
                                @RequestParam("facultyId") int facultyId){
        Faculty f = this.facSer.getFacultyById(facultyId);
        c.setFaculty(f);
        this.classSer.addOrUpdate(c);
        
        return "redirect:/classes";
    }
}
