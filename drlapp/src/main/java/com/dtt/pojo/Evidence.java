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
    @Column(name = "id")
    private String id;

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
     * @return the trainingPoint
     */
    public TrainingPoint getTrainingPoint() {
        return trainingPoint;
    }

    /**
     * @param trainingPoint the trainingPoint to set
     */
    public void setTrainingPoint(TrainingPoint trainingPoint) {
        this.trainingPoint = trainingPoint;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the uploadDate
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate the uploadDate to set
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    /**
     * @return the verifyStatus
     */
    public VerifyStatus getVerifyStatus() {
        return verifyStatus;
    }

    /**
     * @param verifyStatus the verifyStatus to set
     */
    public void setVerifyStatus(VerifyStatus verifyStatus) {
        this.verifyStatus = verifyStatus;
    }
}
