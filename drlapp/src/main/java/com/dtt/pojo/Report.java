package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "reports")
public class Report implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "generated_at")
    private Date generatedAt;

    @OneToMany(mappedBy = "report")
    private Set<ReportDetail> reportDetails;

    // Getters and setters
}
