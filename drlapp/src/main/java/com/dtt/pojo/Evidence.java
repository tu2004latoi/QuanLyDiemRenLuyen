package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "evidences")
@NamedQueries({
    @NamedQuery(name = "Evidence.findAll", query = "SELECT e FROM Evidence e"),
    @NamedQuery(name = "Evidence.findById", query = "SELECT e FROM Evidence e WHERE e.id = :id"),
    @NamedQuery(name = "Evidence.findByStudent", query = "SELECT e FROM Evidence e WHERE e.student = :student"),
    @NamedQuery(name = "Evidence.findByTrainingPoint", query = "SELECT e FROM Evidence e WHERE e.trainingPoint = :trainingPoint"),
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
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "training_point_id")
    private TrainingPoint trainingPoint;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "upload_date")
    private Date uploadDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "verify_status")
    private VerifyStatus verifyStatus;

    public enum VerifyStatus {
        PENDING, APPROVED, REJECTED
    }

    // Getters and setters
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

    public TrainingPoint getTrainingPoint() {
        return trainingPoint;
    }

    public void setTrainingPoint(TrainingPoint trainingPoint) {
        this.trainingPoint = trainingPoint;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
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
        return "Evidence{" +
                "id=" + id +
                ", student=" + student +
                ", trainingPoint=" + trainingPoint +
                ", filePath='" + filePath + '\'' +
                ", uploadDate=" + uploadDate +
                ", verifyStatus=" + verifyStatus +
                '}';
    }
}
