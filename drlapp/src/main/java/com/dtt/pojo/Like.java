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

<<<<<<< HEAD
    // Getter và Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

=======
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
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public Student getStudent() {
        return student;
    }

<<<<<<< HEAD
=======
    /**
     * @param student the student to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setStudent(Student student) {
        this.student = student;
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
     * @return the createdAt
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public Date getCreatedAt() {
        return createdAt;
    }

<<<<<<< HEAD
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
=======
    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
}
