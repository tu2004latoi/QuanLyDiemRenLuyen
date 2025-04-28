package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "likes")
@NamedQueries({
    @NamedQuery(name = "Like.findAll", query = "SELECT l FROM Like l"),
    @NamedQuery(name = "Like.findById", query = "SELECT l FROM Like l WHERE l.id = :id"),
    @NamedQuery(name = "Like.findByStudent", query = "SELECT l FROM Like l WHERE l.student.id = :studentId"),
    @NamedQuery(name = "Like.findByActivity", query = "SELECT l FROM Like l WHERE l.activity.id = :activityId"),
    @NamedQuery(name = "Like.findByCreatedAt", query = "SELECT l FROM Like l WHERE l.createdAt = :createdAt")
})

public class Like implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(name = "created_at")
    private Date createdAt;

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
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * @return the activity
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * @param activity the activity to set
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
