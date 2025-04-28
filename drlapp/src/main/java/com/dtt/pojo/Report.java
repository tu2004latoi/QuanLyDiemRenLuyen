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
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động sinh id
    @Column(name = "id")
    private Integer id;  // Chuyển từ String thành Integer

    @Column(name = "generated_at")
    private Date generatedAt;

    @OneToMany(mappedBy = "report")
    private Set<ReportDetail> reportDetails;

<<<<<<< HEAD
    // Getter và Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

=======
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
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public Date getGeneratedAt() {
        return generatedAt;
    }

<<<<<<< HEAD
=======
    /**
     * @param generatedAt the generatedAt to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setGeneratedAt(Date generatedAt) {
        this.generatedAt = generatedAt;
    }

<<<<<<< HEAD
=======
    /**
     * @return the reportDetails
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public Set<ReportDetail> getReportDetails() {
        return reportDetails;
    }

<<<<<<< HEAD
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
=======
    /**
     * @param reportDetails the reportDetails to set
     */
    public void setReportDetails(Set<ReportDetail> reportDetails) {
        this.reportDetails = reportDetails;
    }
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
}
