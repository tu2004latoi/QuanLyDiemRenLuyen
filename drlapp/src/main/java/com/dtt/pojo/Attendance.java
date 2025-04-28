package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "attendances")
public class Attendance implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "timestamp")
    private Date timestamp;

    public enum Status {
        PRESENT, ABSENT, LATE
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id='" + id + '\'' +
                ", activity=" + activity +
                ", staff=" + staff +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }
}
