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
    @Column(name = "id")
    private String id;

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
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the studentId
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * @return the className
     */
    public String getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */
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
     * @return the trainingPoints
     */
    public Set<TrainingPoint> getTrainingPoints() {
        return trainingPoints;
    }

    /**
     * @param trainingPoints the trainingPoints to set
     */
    public void setTrainingPoints(Set<TrainingPoint> trainingPoints) {
        this.trainingPoints = trainingPoints;
    }

    /**
     * @return the evidences
     */
    public Set<Evidence> getEvidences() {
        return evidences;
    }

    /**
     * @param evidences the evidences to set
     */
    public void setEvidences(Set<Evidence> evidences) {
        this.evidences = evidences;
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
