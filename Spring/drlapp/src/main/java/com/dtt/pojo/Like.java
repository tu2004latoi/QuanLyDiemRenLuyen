package com.dtt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "likes")
@NamedQueries({
    @NamedQuery(name = "Like.findAll", query = "SELECT l FROM Like l"),
    @NamedQuery(name = "Like.findById", query = "SELECT l FROM Like l WHERE l.id = :id"),
    @NamedQuery(name = "Like.findByUserId", query = "SELECT l FROM Like l WHERE l.user.id = :userId"),
    @NamedQuery(name = "Like.findByActivityId", query = "SELECT l FROM Like l WHERE l.activity.id = :activityId"),
    @NamedQuery(name = "Like.findByUserIdAndActivityId", query = "SELECT l FROM Like l WHERE l.user.id = :userId AND l.activity.id = :activityId"),
    @NamedQuery(name = "Like.findByCreatedAt", query = "SELECT l FROM Like l WHERE l.createdAt = :createdAt")

})

public class Like implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động sinh id
    @Column(name = "id")
    private Integer id;  // Thay đổi từ String thành Integer

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Getter và Setter
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Like{"
                + "id=" + id
                + ", student=" + user
                + ", activity=" + activity
                + ", createdAt=" + createdAt
                + '}';
    }
}
