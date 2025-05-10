package com.dtt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động sinh id
    @Column(name = "id")
    private Integer id;  // Thay đổi từ String thành Integer

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(name = "created_at")
    private Date createdAt;

    // Getter và Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", student=" + student +
                ", activity=" + activity +
                ", createdAt=" + createdAt +
                '}';
    }
}
