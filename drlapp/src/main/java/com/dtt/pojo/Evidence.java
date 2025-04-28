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
<<<<<<< HEAD
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

=======
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
     * @return the trainingPoint
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public TrainingPoint getTrainingPoint() {
        return trainingPoint;
    }

<<<<<<< HEAD
=======
    /**
     * @param trainingPoint the trainingPoint to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setTrainingPoint(TrainingPoint trainingPoint) {
        this.trainingPoint = trainingPoint;
    }

<<<<<<< HEAD
=======
    /**
     * @return the filePath
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public String getFilePath() {
        return filePath;
    }

<<<<<<< HEAD
=======
    /**
     * @param filePath the filePath to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

<<<<<<< HEAD
=======
    /**
     * @return the uploadDate
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public Date getUploadDate() {
        return uploadDate;
    }

<<<<<<< HEAD
=======
    /**
     * @param uploadDate the uploadDate to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

<<<<<<< HEAD
=======
    /**
     * @return the verifyStatus
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public VerifyStatus getVerifyStatus() {
        return verifyStatus;
    }

<<<<<<< HEAD
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
=======
    /**
     * @param verifyStatus the verifyStatus to set
     */
    public void setVerifyStatus(VerifyStatus verifyStatus) {
        this.verifyStatus = verifyStatus;
    }
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
}
