package com.dtt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email LIKE :email"),
    @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName LIKE :firstName"),
    @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName LIKE :lastName"),
    @NamedQuery(name = "User.findByAvatar", query = "SELECT u FROM User u WHERE u.avatar = :avatar")
})

public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tạo giá trị ID
    @Column(name = "id")
    private Integer id; // Thay đổi từ String thành Integer

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "points")
    private int points;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "active")
    private boolean active;

    @Transient
    private MultipartFile file;

    @OneToMany(mappedBy = "confirmedBy")
    @JsonIgnore
    private Set<TrainingPoint> confirmedTrainingPoints;
    
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Evidence> evidences;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Admin admin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ActivityRegistrations> activityRegistrations = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<TrainingPoint> trainingPoints;

    public Set<ActivityRegistrations> getActivityRegistrations() {
        return activityRegistrations;
    }

    public void setActivityRegistrations(Set<ActivityRegistrations> activityRegistrations) {
        this.activityRegistrations = activityRegistrations;
    }

    public Set<TrainingPoint> getTrainingPoints() {
        return trainingPoints;
    }

    public void setTrainingPoints(Set<TrainingPoint> trainingPoints) {
        this.trainingPoints = trainingPoints;
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
    
    public Set<Evidence> getEvidences() {
        return evidences;
    }

    public void setEvidences(Set<Evidence> evidences) {
        this.evidences = evidences;
    }
    
    public Set<TrainingPoint> getConfirmedTrainingPoints() {
        return confirmedTrainingPoints;
    }

    public void setConfirmedTrainingPoints(Set<TrainingPoint> confirmedTrainingPoints) {
        this.confirmedTrainingPoints = confirmedTrainingPoints;
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

    public String getName() {
        return firstName + " " + lastName;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Staff staff;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Student student;

    public enum Role {
        ADMIN, STAFF, STUDENT
    }
}
