package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "staff")
@NamedQueries({
    @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s"),
    @NamedQuery(name = "Staff.findById", query = "SELECT s FROM Staff s WHERE s.id = :id"),
    @NamedQuery(name = "Staff.findByFaculty", query = "SELECT s FROM Staff s WHERE s.faculty.id = :facultyId"),
    @NamedQuery(name = "Staff.findByUser", query = "SELECT s FROM Staff s WHERE s.user.id = :userId")
})

public class Staff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động sinh id
    @Column(name = "id")
    private Integer id; // Chuyển từ String thành Integer

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    private Faculty faculty;

    @OneToMany(mappedBy = "organizer")
    private Set<Activity> activities;

    @OneToMany(mappedBy = "confirmedBy")
    private Set<TrainingPoint> confirmedTrainingPoints;

    @OneToMany(mappedBy = "staff")
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

    public Set<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
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
<<<<<<< HEAD
        return "Staff{" +
                "id=" + id + // Chuyển từ String sang Integer
                ", faculty=" + faculty +
                '}';
=======
        return "Staff{"
                + "id='" + id + '\''
                + ", faculty=" + faculty
                + '}';
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    }
}
