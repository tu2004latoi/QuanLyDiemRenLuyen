package com.dtt.pojo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "evidences")
@NamedQueries({
    @NamedQuery(name = "Evidence.findAll", query = "SELECT e FROM Evidence e"),
    @NamedQuery(name = "Evidence.findById", query = "SELECT e FROM Evidence e WHERE e.id = :id"),
    @NamedQuery(name = "Evidence.findByUser", query = "SELECT e FROM Evidence e WHERE e.user = :user"),
    @NamedQuery(name = "Evidence.findByTrainingPoint", query = "SELECT e FROM Evidence e WHERE e.trainingPoint = :trainingPoint"),
    @NamedQuery(name = "Evidence.findByTrainingPointId", query = "SELECT e FROM Evidence e WHERE e.trainingPoint.id = :trainingPointId"),
    @NamedQuery(name = "Evidence.findByActivityRegistration", query = "SELECT e FROM Evidence e WHERE e.activityRegistration = :activityRegistration"),
    @NamedQuery(name = "Evidence.findByActivityRegistrationId", query = "SELECT e FROM Evidence e WHERE e.activityRegistration.id = :activityRegistrationId"),
    @NamedQuery(name = "Evidence.findByFilePath", query = "SELECT e FROM Evidence e WHERE e.filePath = :filePath"),
    @NamedQuery(name = "Evidence.findByUploadDate", query = "SELECT e FROM Evidence e WHERE e.uploadDate = :uploadDate"),
    @NamedQuery(name = "Evidence.findByVerifyStatus", query = "SELECT e FROM Evidence e WHERE e.verifyStatus = :verifyStatus")
})
public class Evidence implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động sinh id
    @Column(name = "id")
    private Integer id;  // Thay đổi từ String thành Integer

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "training_point_id")
    private TrainingPoint trainingPoint;

    @ManyToOne
    @JoinColumn(name = "activity_registration_id", referencedColumnName = "id", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private ActivityRegistrations activityRegistration;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "verify_status")
    private VerifyStatus verifyStatus;

    public enum VerifyStatus {
        PENDING, APPROVED, REJECTED
    }

    @Transient
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    // Getters and setters
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

    public TrainingPoint getTrainingPoint() {
        return trainingPoint;
    }

    public void setTrainingPoint(TrainingPoint trainingPoint) {
        this.trainingPoint = trainingPoint;
    }

    public ActivityRegistrations getActivityRegistration() {
        return activityRegistration;
    }

    public void setActivityRegistration(ActivityRegistrations activityRegistration) {
        this.activityRegistration = activityRegistration;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public VerifyStatus getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(VerifyStatus verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    @Override
    public String toString() {
        return "Evidence{"
                + "id=" + id
                + ", user=" + user
                + ", trainingPoint=" + trainingPoint
                + ", filePath='" + filePath + '\''
                + ", uploadDate=" + uploadDate
                + ", verifyStatus=" + verifyStatus
                + '}';
    }
}
