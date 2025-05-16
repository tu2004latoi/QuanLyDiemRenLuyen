package com.dtt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "staff")
@NamedQueries({
    @NamedQuery(name = "Staff.findById", query = "SELECT s FROM Staff s WHERE s.id = :id"),
    @NamedQuery(name = "Staff.findByUserId", query = "SELECT s FROM Staff s WHERE s.user.id = :userId"),
    @NamedQuery(name = "Staff.findByFaculty", query = "SELECT s FROM Staff s WHERE s.faculty.id = :facultyId"),
})
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

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id + // Chuyển từ String sang Integer
                ", faculty=" + faculty +
                '}';
    }
}
