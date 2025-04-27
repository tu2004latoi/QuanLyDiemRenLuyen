package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "training_points")
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
}
