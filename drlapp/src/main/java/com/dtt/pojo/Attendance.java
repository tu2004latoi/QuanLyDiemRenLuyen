package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "attendances")
@NamedQueries({
    @NamedQuery(name = "Attendance.findAll", query = "SELECT a FROM Attendance a"),
    @NamedQuery(name = "Attendance.findById", query = "SELECT a FROM Attendance a WHERE a.id = :id"),
    @NamedQuery(name = "Attendance.findByActivity", query = "SELECT a FROM Attendance a WHERE a.activity = :activity"),
    @NamedQuery(name = "Attendance.findByStaff", query = "SELECT a FROM Attendance a WHERE a.staff = :staff"),
    @NamedQuery(name = "Attendance.findByStatus", query = "SELECT a FROM Attendance a WHERE a.status = :status"),
    @NamedQuery(name = "Attendance.findByTimestamp", query = "SELECT a FROM Attendance a WHERE a.timestamp = :timestamp")
})
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
<<<<<<< HEAD
=======
    /**
     * @return the id
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public String getId() {
        return id;
    }

<<<<<<< HEAD
=======
    /**
     * @param id the id to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setId(String id) {
        this.id = id;
    }

<<<<<<< HEAD
=======
    /**
     * @return the activity
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public Activity getActivity() {
        return activity;
    }

<<<<<<< HEAD
=======
    /**
     * @param activity the activity to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

<<<<<<< HEAD
=======
    /**
     * @return the staff
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public Staff getStaff() {
        return staff;
    }

<<<<<<< HEAD
=======
    /**
     * @param staff the staff to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setStaff(Staff staff) {
        this.staff = staff;
    }

<<<<<<< HEAD
=======
    /**
     * @return the status
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public Status getStatus() {
        return status;
    }

<<<<<<< HEAD
=======
    /**
     * @param status the status to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setStatus(Status status) {
        this.status = status;
    }

<<<<<<< HEAD
=======
    /**
     * @return the timestamp
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public Date getTimestamp() {
        return timestamp;
    }

<<<<<<< HEAD
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
=======
    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
}
