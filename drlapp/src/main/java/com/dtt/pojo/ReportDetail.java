package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "report_details")
public class ReportDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Sử dụng Integer thay vì String cho id

    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report; // Quan hệ N:1 với Report

    @ManyToOne
    @JoinColumn(name = "training_point_id")
    private TrainingPoint trainingPoint; // Quan hệ N:1 với TrainingPoint

    // Getter và Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public TrainingPoint getTrainingPoint() {
        return trainingPoint;
    }

    public void setTrainingPoint(TrainingPoint trainingPoint) {
        this.trainingPoint = trainingPoint;
    }

    @Override
    public String toString() {
        return "ReportDetail{" +
                "id=" + id +
                ", report=" + report +
                ", trainingPoint=" + trainingPoint +
                '}';
    }
}
