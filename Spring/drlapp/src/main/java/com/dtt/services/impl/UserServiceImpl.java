/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dtt.pojo.Student;
import com.dtt.pojo.User;
import com.dtt.repositories.ClassRoomRepository;
import com.dtt.repositories.FacultyRepository;
import com.dtt.repositories.UserRepository;
import com.dtt.secutiry.CustomUserDetails;
import com.dtt.services.StudentService;
import com.dtt.services.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author MR TU
 */
@Service("userDetailsService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FacultyRepository facultyRepo;

    @Autowired
    private ClassRoomRepository classRepo;

    @Autowired
    private StudentService studentSer;

    @Autowired
    private UserRepository userRepo;

    @Override
    public User getUserByUsername(String username) {
        return userRepo.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid username");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + u.getRole().name()));

        return new CustomUserDetails(u.getId(), u.getEmail(), u.getPassword(), authorities);
    }

    @Override
    public User register(Map<String, String> params, MultipartFile avatar) {
        User u = new User();
        u.setFirstName(params.get("firstName"));
        u.setLastName(params.get("lastName"));
        u.setEmail(params.get("email"));
        u.setPassword(this.passwordEncoder.encode(params.get("password")));
        u.setPoint_1(0);
        u.setPoint_2(0);
        u.setPoint_3(0);
        u.setPoint_4(0);
        u.setRole(User.Role.valueOf(params.get("role")));

        if (!avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                ex.printStackTrace(); // Ghi log nếu lỗi
            }
        }

        // GỌI DUY NHẤT 1 LẦN
        User savedUser = this.userRepo.register(u);

        if (u.getRole() == User.Role.STUDENT) {
            Student s = new Student();
            s.setUser(savedUser);
            s.setFaculty(facultyRepo.getFacultyById(Integer.parseInt(params.get("facultyId"))));
            s.setClassRoom(classRepo.getClassRoomById(Integer.parseInt(params.get("classRoomId"))));
            studentSer.addOrUpdateStudent(s);
        }

        return savedUser; // Trả về user đã lưu
    }

    @Override
    public User getUserById(int id) {
        return this.userRepo.getUserById(id);
    }

    @Override
    public List<User> getUsers(Map<String, String> params) {
        return this.userRepo.getUsers(params);
    }

    @Override
    public User addOrUpdateUser(User u) {

        if (u.getFile() != null && !u.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(u.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ActivityServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (u.getPassword() != null && !u.getPassword().isEmpty()) {
            u.setPassword(this.passwordEncoder.encode(u.getPassword()));
        } else if (u.getId() != null) {
            User userSaved = this.getUserById(u.getId());
            u.setPassword(userSaved.getPassword());
        }
        return this.userRepo.addOrUpdateUser(u);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepo.getAllUsers();
    }

    @Override
    public void deleteUserById(int id) {
        this.userRepo.deleteUserById(id);
    }

    @Override
    public long getCountUsers() {
        return this.userRepo.getCountUsers();
    }

    @Override
    public User updatePointUser(User u) {
        User user = this.userRepo.getUserById(u.getId());
        user.setPoint_1(u.getPoint_1());
        user.setPoint_2(u.getPoint_2());
        user.setPoint_3(u.getPoint_3());
        user.setPoint_4(u.getPoint_4());

        return this.userRepo.addOrUpdateUser(u);
    }

    @Override
    public List<User> getAllStudents() {
        return this.userRepo.getAllStudents();
    }

    @Override
    public void flush() {
        entityManager.flush();
    }

    public boolean authenticate(String username, String password) {
        return this.userRepo.authenticate(username, password);
    }

    @Override
    public List<User> getUsersExcept(String username) {
        return this.userRepo.getAllUsers()
        .stream()
        .filter(u -> !u.getEmail().equals(username))
        .collect(Collectors.toList());
    }
}
