package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "attendances")
@NamedQueries({
    @NamedQuery(name = "Attendance.findAll", query = "SELECT a FROM Attendance a"),
    @NamedQuery(name = "Attendance.findById", query = "SELECT a FROM Attendance a WHERE a.id = :id"),
    @NamedQuery(name = "Attendance.findByActivity", query = "SELECT a FROM Attendance a WHERE a.activity = :activity"),
    @NamedQuery(name = "Attendance.findByUser", query = "SELECT a FROM Attendance a WHERE a.user = :user"),
    @NamedQuery(name = "Attendance.findByStatus", query = "SELECT a FROM Attendance a WHERE a.status = :status"),
    @NamedQuery(name = "Attendance.findByTimestamp", query = "SELECT a FROM Attendance a WHERE a.timestamp = :timestamp")
})
public class Attendance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động sinh id
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    @Column(name="is_register")
    private Boolean isRegister;

    public Boolean getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(Boolean isRegister) {
        this.isRegister = isRegister;
    }

    public enum Status {
        PRESENT, ABSENT, LATE
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id='" + id + '\'' +
                ", activity=" + activity +
                ", user=" + user +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }
}
