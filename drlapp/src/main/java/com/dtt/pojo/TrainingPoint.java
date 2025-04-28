package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "training_points")
@NamedQueries({
    @NamedQuery(name = "TrainingPoint.findAll", query = "SELECT t FROM TrainingPoint t"),
    @NamedQuery(name = "TrainingPoint.findById", query = "SELECT t FROM TrainingPoint t WHERE t.id = :id"),
    @NamedQuery(name = "TrainingPoint.findByStudent", query = "SELECT t FROM TrainingPoint t WHERE t.student.id = :studentId"),
    @NamedQuery(name = "TrainingPoint.findByActivity", query = "SELECT t FROM TrainingPoint t WHERE t.activity.id = :activityId"),
    @NamedQuery(name = "TrainingPoint.findByStatus", query = "SELECT t FROM TrainingPoint t WHERE t.status = :status"),
    @NamedQuery(name = "TrainingPoint.findByConfirmedBy", query = "SELECT t FROM TrainingPoint t WHERE t.confirmedBy.id = :staffId"),
    @NamedQuery(name = "TrainingPoint.findByDateAwarded", query = "SELECT t FROM TrainingPoint t WHERE t.dateAwarded = :dateAwarded")
})

public class TrainingPoint implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Column(name = "point")
    private Integer point;

    @Column(name = "date_awarded")
    private Date dateAwarded;

    @ManyToOne
    @JoinColumn(name = "confirmed_by")
    private Staff confirmedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "trainingPoint")
    private Set<Evidence> evidences;

    @OneToMany(mappedBy = "trainingPoint")
    private Set<ReportDetail> reportDetails;

    public enum Status {
        PENDING, CONFIRMED, REJECTED
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
     * @return the activity
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * @param activity the activity to set
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * @return the point
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(Integer point) {
        this.point = point;
    }

    /**
     * @return the dateAwarded
     */
    public Date getDateAwarded() {
        return dateAwarded;
    }

    /**
     * @param dateAwarded the dateAwarded to set
     */
    public void setDateAwarded(Date dateAwarded) {
        this.dateAwarded = dateAwarded;
    }

    /**
     * @return the confirmedBy
     */
    public Staff getConfirmedBy() {
        return confirmedBy;
    }

    /**
     * @param confirmedBy the confirmedBy to set
     */
    public void setConfirmedBy(Staff confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the evidences
     */
    public Set<Evidence> getEvidences() {
        return evidences;
    }

    /**
     * @param evidences the evidences to set
     */
    public void setEvidences(Set<Evidence> evidences) {
        this.evidences = evidences;
    }

    /**
     * @return the reportDetails
     */
    public Set<ReportDetail> getReportDetails() {
        return reportDetails;
    }

    /**
     * @param reportDetails the reportDetails to set
     */
    public void setReportDetails(Set<ReportDetail> reportDetails) {
        this.reportDetails = reportDetails;
    }
}
