package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity_registrations")
@NamedQueries({
    @NamedQuery(name = "ActivityRegistrations.findAll",
            query = "SELECT ar FROM ActivityRegistrations ar"),

    @NamedQuery(name = "ActivityRegistrations.findById",
            query = "SELECT ar FROM ActivityRegistrations ar WHERE ar.id = :id"),

    @NamedQuery(name = "ActivityRegistrations.findByUserId",
            query = "SELECT ar FROM ActivityRegistrations ar WHERE ar.user.id = :userId"),

    @NamedQuery(name = "ActivityRegistrations.findByActivityId",
            query = "SELECT ar FROM ActivityRegistrations ar WHERE ar.activity.id = :activityId"),

    @NamedQuery(name = "ActivityRegistrations.findByUserAndActivity",
            query = "SELECT ar FROM ActivityRegistrations ar WHERE ar.user.id = :userId AND ar.activity.id = :activityId"),

    @NamedQuery(name = "ActivityRegistrations.countByActivityId",
            query = "SELECT COUNT(ar) FROM ActivityRegistrations ar WHERE ar.activity.id = :activityId")
})

public class ActivityRegistrations implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    public ActivityRegistrations() {
    }

    public ActivityRegistrations(User user, Activity activity, LocalDateTime registrationDate) {
        this.user = user;
        this.activity = activity;
        this.registrationDate = registrationDate;
    }

    // Getters và Setters
    public Integer getId() {
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "ActivityRegistrations{"
                + "id=" + id
                + ", user=" + (user != null ? user.getId() : null)
                + ", activity=" + (activity != null ? activity.getId() : null)
                + ", registrationDate=" + registrationDate
                + '}';
    }
}
