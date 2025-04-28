package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "admins")
public class Admin implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "department")
    private String department;

    // Getter và Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id='" + id + '\'' +
                ", department='" + department + '\'' +
                ", user=" + user +
                '}';
    }
}
