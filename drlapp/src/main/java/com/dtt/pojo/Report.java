package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "reports")
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r"),
    @NamedQuery(name = "Report.findById", query = "SELECT r FROM Report r WHERE r.id = :id"),
    @NamedQuery(name = "Report.findByGeneratedAt", query = "SELECT r FROM Report r WHERE r.generatedAt = :generatedAt")
})

public class Report implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "generated_at")
    private Date generatedAt;

    @OneToMany(mappedBy = "report")
    private Set<ReportDetail> reportDetails;

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
     * @return the generatedAt
     */
    public Date getGeneratedAt() {
        return generatedAt;
    }

    /**
     * @param generatedAt the generatedAt to set
     */
    public void setGeneratedAt(Date generatedAt) {
        this.generatedAt = generatedAt;
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
