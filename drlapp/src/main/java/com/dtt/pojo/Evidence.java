package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "evidences")
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
}
