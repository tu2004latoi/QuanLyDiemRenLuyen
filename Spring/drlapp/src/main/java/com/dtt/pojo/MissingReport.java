package com.dtt.pojo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "missing_reports")
@NamedQueries({
    @NamedQuery(name = "MissingReport.findAll", query = "SELECT mr FROM MissingReport mr"),
    @NamedQuery(name = "MissingReport.findByUserId", query = "SELECT mr FROM MissingReport mr WHERE mr.user.id = :userId"),
    @NamedQuery(name = "MissingReport.findByActivityId", query = "SELECT mr FROM MissingReport mr WHERE mr.activity.id = :activityId"),
    @NamedQuery(name = "MissingReport.findByTrainingPointId", query = "SELECT mr FROM MissingReport mr WHERE mr.trainingPoint.id = :trainingPointId"),
    @NamedQuery(name = "MissingReport.findByStatus", query = "SELECT mr FROM MissingReport mr WHERE mr.status = :status"),
    @NamedQuery(name = "MissingReport.countByUserId", query = "SELECT COUNT(mr) FROM MissingReport mr WHERE mr.user.id = :userId"),})
public class MissingReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;  // Liên kết với bảng Users

    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "id", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
    @JsonIdentityReference(alwaysAsId = true)
    private Activity activity;  // Liên kết với bảng Activities

    @ManyToOne
    @JoinColumn(name = "training_point_id", referencedColumnName = "id", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private TrainingPoint trainingPoint;  // Liên kết với bảng TrainingPoints

    @Column(name = "point")
    private Integer point;

    @Column(name = "date_report")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date dateReport;

    @Column(name = "image")
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReportStatus status;
    
    @Transient
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public TrainingPoint getTrainingPoint() {
        return trainingPoint;
    }

    public void setTrainingPoint(TrainingPoint trainingPoint) {
        this.trainingPoint = trainingPoint;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Date getDateReport() {
        return dateReport;
    }

    public void setDateReport(Date dateReport) {
        this.dateReport = dateReport;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    // Enum đại diện cho trạng thái báo thiếu
    public enum ReportStatus {
        PENDING, CONFIRMED, REJECTED
    }

    @Override
    public String toString() {
        return "MissingReport{"
                + "id=" + id
                + ", user=" + (user != null ? user.getId() : null)
                + ", activity=" + (activity != null ? activity.getId() : null)
                + ", trainingPoint=" + (trainingPoint != null ? trainingPoint.getId() : null)
                + ", point=" + point
                + ", dateReport=" + dateReport
                + ", image='" + image + '\''
                + ", status=" + status
                + '}';
    }
}
