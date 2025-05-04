package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "students")
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
    @NamedQuery(name = "Student.findById", query = "SELECT s FROM Student s WHERE s.id = :id"),
    @NamedQuery(name = "Student.findByStudentId", query = "SELECT s FROM Student s WHERE s.studentId = :studentId"),
    @NamedQuery(name = "Student.findByFaculty", query = "SELECT s FROM Student s WHERE s.faculty.id = :facultyId"),
    @NamedQuery(name = "Student.findByClass", query = "SELECT s FROM Student s WHERE s.className = :className")
})

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
    private Set<Comment> comments;

    @OneToMany(mappedBy = "student")
    private Set<Like> likes;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the faculty
     */
    public Faculty getFaculty() {
        return faculty;
    }

    /**
     * @param faculty the faculty to set
     */
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    /**
     * @return the comments
     */
    public Set<Comment> getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    /**
     * @return the likes
     */
    public Set<Like> getLikes() {
        return likes;
    }

    /**
     * @param likes the likes to set
     */
    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }
}
