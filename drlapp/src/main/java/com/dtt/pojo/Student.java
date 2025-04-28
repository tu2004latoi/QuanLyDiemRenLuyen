package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tạo id
    @Column(name = "id")
    private Integer id; // Thay đổi từ String thành Integer

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "student_id", nullable = false, unique = true)
    private String studentId;

    @Column(name = "class")
    private String className;

    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    private Faculty faculty;

    @OneToMany(mappedBy = "student")
    private Set<TrainingPoint> trainingPoints;

    @OneToMany(mappedBy = "student")
    private Set<Evidence> evidences;

    @OneToMany(mappedBy = "student")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "student")
    private Set<Like> likes;

    // Getters and setters
}
