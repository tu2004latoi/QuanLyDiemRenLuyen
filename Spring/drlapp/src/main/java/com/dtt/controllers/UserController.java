/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Admin;
import com.dtt.pojo.ClassRoom;
import com.dtt.pojo.Department;
import com.dtt.pojo.Faculty;
import com.dtt.pojo.Staff;
import com.dtt.pojo.Student;
import com.dtt.pojo.User;
import com.dtt.services.ActivityRegistrationService;
import com.dtt.services.AdminService;
import com.dtt.services.ClassRoomService;
import com.dtt.services.DepartmentService;
import com.dtt.services.FacultyService;
import com.dtt.services.StaffService;
import com.dtt.services.StudentService;
import com.dtt.services.UserService;
import com.dtt.services.impl.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author MR TU
 */
@Controller
public class UserController {

    @Autowired
    private UserService userSer;

    @Autowired
    private ActivityRegistrationService arSer;

    @Autowired
    private StudentService stSer;

    @Autowired
    private FacultyService facSer;

    @Autowired
    private ClassRoomService classRoomSer;
    
    @Autowired
    private StaffService staffSer;

    @Autowired
    private DepartmentService departmentSer;
    
    @Autowired
    private AdminService adminSer;
    
    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/register")
    public String registerView() {
        return "register";
    }

    @GetMapping("/users/register")
    public String userRegisterView() {
        return "listUserRegister";
    }

    @GetMapping("/users/list")
    public String userListiew(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("users", this.userSer.getUsers(params));
        return "listUser";
    }

    @GetMapping("/users")
    public String userManageView() {
        return "usersManage";
    }

    @GetMapping("/users/update/{id}")
    public String userDetails(Model model, @PathVariable("id") int id) {
        User u = userSer.getUserById(id);

        model.addAttribute("user", u);
        model.addAttribute("faculties", this.facSer.getAllFaculties());
        model.addAttribute("departments", this.departmentSer.getAllDepartments());
        return "userDetails";
    }

    @GetMapping("/users/add")
    public String manageUser(Model model) {
        User u = new User();

        model.addAttribute("user", u);
        model.addAttribute("faculties", this.facSer.getAllFaculties());
        model.addAttribute("classes", this.classRoomSer.getAllClassRooms());
        model.addAttribute("departments", this.departmentSer.getAllDepartments());
        return "userDetails";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute("user") @Valid User u,
            @RequestParam(value = "classRoom", required = false) Integer classRoomId,
            @RequestParam(value = "faculty", required = false) Integer facultyId,
            @RequestParam(value = "department", required = false) Integer departmentId,
            Model model) {
        try {
            if (u.getRole() == User.Role.STUDENT) {
                u.setStaff(null);
                u.setAdmin(null);
                this.userSer.addOrUpdateUser(u);
                if (classRoomId == null || facultyId == null) {
                    throw new IllegalArgumentException("Lớp hoặc khoa không được để trống");
                }

                ClassRoom cr = this.classRoomSer.getClassRoomById(classRoomId);
                Faculty f = this.facSer.getFacultyById(facultyId);

                Student stSaved = this.stSer.getStudentByUserId(u.getId());
                if (stSaved == null) {
                    Student st = new Student();
                    st.setUser(u);
                    st.setClassRoom(cr);
                    st.setFaculty(f);
                    this.stSer.addOrUpdateStudent(st);
                } else {
                    u.getStudent().setClassRoom(cr);
                    u.getStudent().setFaculty(f);
                    this.stSer.addOrUpdateStudent(u.getStudent());
                }
            } else if (u.getRole() == User.Role.STAFF) {
                u.setAdmin(null);
                u.setStudent(null);
                this.userSer.addOrUpdateUser(u);
                if (facultyId == null) {
                    throw new IllegalArgumentException("Khoa không được để trống");
                }

                Faculty f = this.facSer.getFacultyById(facultyId);

                Staff staffSaved = this.staffSer.getStaffByUserId(u.getId());
                if (staffSaved == null) {
                    Staff s = new Staff();
                    s.setUser(u);
                    s.setFaculty(f);
                    this.staffSer.addOrUpdateStaff(s);
                } else {
                    u.getStaff().setFaculty(f);
                    this.staffSer.addOrUpdateStaff(u.getStaff());
                }
            } else {
                u.setStudent(null);
                u.setStaff(null);
                this.userSer.addOrUpdateUser(u);
                if (departmentId == null) {
                    throw new IllegalArgumentException("Phòng không được để trống");
                }

                Department department = this.departmentSer.getDepartmentById(departmentId);

                Admin adSaved = this.adminSer.getAdminByUserId(u.getId());
                if (adSaved == null) {
                    Admin ad = new Admin();
                    ad.setUser(u);
                    ad.setDepartment(department);
                    this.adminSer.addOrUpdateAdmin(ad);
                } else {
                    u.getAdmin().setDepartment(department);
                    this.adminSer.addOrUpdateAdmin(u.getAdmin());
                }
            }

            return "redirect:/users/list";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("emailError", ex.getMessage());
            model.addAttribute("user", u);
            return "userDetails";
        }
    }

    @GetMapping("/users/activity-registrations")
    public String activityRegistrationsView(Model model) {
        model.addAttribute("ar", this.arSer.getAllActivityRegistrations());
        return "listUserRegister";
    }

}
