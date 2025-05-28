package com.dtt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "training_points")
@NamedQueries({
    @NamedQuery(name = "TrainingPoint.findAll", query = "SELECT t FROM TrainingPoint t"),
    @NamedQuery(name = "TrainingPoint.findById", query = "SELECT t FROM TrainingPoint t WHERE t.id = :id"),
    @NamedQuery(name = "TrainingPoint.findByUser", query = "SELECT t FROM TrainingPoint t WHERE t.user.id = :userId"),
    @NamedQuery(name = "TrainingPoint.findByActivity", query = "SELECT t FROM TrainingPoint t WHERE t.activity.id = :activityId"),
    @NamedQuery(name = "TrainingPoint.findByStatus", query = "SELECT t FROM TrainingPoint t WHERE t.status = :status"),
    @NamedQuery(name = "TrainingPoint.findByConfirmedBy", query = "SELECT t FROM TrainingPoint t WHERE t.confirmedBy.id = :userId"),
    @NamedQuery(name = "TrainingPoint.findByDateAwarded", query = "SELECT t FROM TrainingPoint t WHERE t.dateAwarded = :dateAwarded"),
    @NamedQuery(name = "TrainingPoint.findByUserIdAndActivityId",
            query = "SELECT t FROM TrainingPoint t WHERE t.user.id = :userId AND t.activity.id = :activityId")
})

public class TrainingPoint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tạo giá trị IDENTITY
    @Column(name = "id")
    private Integer id; // Thay đổi từ String thành Integer

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(name = "point")
    private Integer point;

    @Column(name = "date_awarded")
    private LocalDateTime dateAwarded;

    @ManyToOne
    @JoinColumn(name = "confirmed_by")
    @JsonIgnore
    private User confirmedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToOne(mappedBy = "trainingPoint", orphanRemoval = true)
    @JsonIgnore
    private Evidence evidences;

    public enum Status {
        PENDING, CONFIRMED, REJECTED
    }

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

    /**
     * @return the activity
     */
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public LocalDateTime getDateAwarded() {
        return dateAwarded;
    }

    public void setDateAwarded(LocalDateTime dateAwarded) {
        this.dateAwarded = dateAwarded;
    }

    public User getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(User confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Evidence getEvidences() {
        return evidences;
    }

    public void setEvidences(Evidence evidences) {
        this.evidences = evidences;
    }
}
