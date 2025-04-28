package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "reports")
public class Report implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động sinh id
    @Column(name = "id")
    private Integer id;  // Chuyển từ String thành Integer

    @Column(name = "generated_at")
    private Date generatedAt;

    @OneToMany(mappedBy = "report")
    private Set<ReportDetail> reportDetails;

    // Getter và Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Date generatedAt) {
        this.generatedAt = generatedAt;
    }

    public Set<ReportDetail> getReportDetails() {
        return reportDetails;
    }

    public void setReportDetails(Set<ReportDetail> reportDetails) {
        this.reportDetails = reportDetails;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", generatedAt=" + generatedAt +
                '}';
    }
}
