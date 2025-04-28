package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(
        name = "User.findByUsername",
        query = "FROM User WHERE email = :username"
    )
})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tạo giá trị ID
    @Column(name = "id")
    private Integer id; // Thay đổi từ String thành Integer

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "avatar")
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Admin admin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Staff staff;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Student student;

    // Getters and setters

    public enum Role {
        ADMIN, STAFF, STUDENT
    }
}
