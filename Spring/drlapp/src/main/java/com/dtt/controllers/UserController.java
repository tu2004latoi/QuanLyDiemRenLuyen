/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.User;
import com.dtt.services.ActivityRegistrationService;
import com.dtt.services.UserService;
import com.dtt.services.impl.UserServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "userDetails";
    }

    @GetMapping("/users/add")
    public String manageUser(Model model) {
        User u = new User();
        model.addAttribute("user", u);
        return "userDetails";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute("user") @Valid User u, Model model) {
        this.userSer.addOrUpdateUser(u);
        return "redirect:/users/list";
    }
    
    @GetMapping("/users/activity-registrations")
    public String activityRegistrationsView(Model model){
        model.addAttribute("ar", this.arSer.getAllActivityRegistrations());
        return "listUserRegister";
    }
}
