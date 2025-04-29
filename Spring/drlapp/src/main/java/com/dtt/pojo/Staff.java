package com.dtt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "staff")
public class Staff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động sinh id
    @Column(name = "id")
    private Integer id; // Chuyển từ String thành Integer

    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    private Faculty faculty;

    @OneToMany(mappedBy = "confirmedBy")
    @JsonIgnore
    private Set<TrainingPoint> confirmedTrainingPoints;

    @OneToMany(mappedBy = "staff")
    @JsonIgnore
    private Set<Attendance> attendances;

    // Getter và Setter
    public Integer getId() {  // Chuyển từ String thành Integer
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Set<TrainingPoint> getConfirmedTrainingPoints() {
        return confirmedTrainingPoints;
    }

    public void setConfirmedTrainingPoints(Set<TrainingPoint> confirmedTrainingPoints) {
        this.confirmedTrainingPoints = confirmedTrainingPoints;
    }

    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id + // Chuyển từ String sang Integer
                ", faculty=" + faculty +
                '}';
    }
}
