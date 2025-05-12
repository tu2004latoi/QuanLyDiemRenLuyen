package com.dtt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "faculties")
@NamedQueries({
    @NamedQuery(name = "Faculty.findAll", query = "SELECT f FROM Faculty f"),
    @NamedQuery(name = "Faculty.findById", query = "SELECT f FROM Faculty f WHERE f.id = :id"),
    @NamedQuery(name = "Faculty.findByName", query = "SELECT f FROM Faculty f WHERE f.name = :name"),
    @NamedQuery(name = "Faculty.findByDescription", query = "SELECT f FROM Faculty f WHERE f.description = :description")
})
public class Faculty implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Tự động sinh id
    @Column(name = "id")
    private Integer id;  // Thay đổi từ String thành Integer

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    // Quan hệ 1-N với Staff (một Faculty có thể có nhiều Staff)
    @OneToMany(mappedBy = "faculty")
    @JsonIgnore
    private Set<Staff> staff;

    @OneToMany(mappedBy = "faculty")
    @JsonIgnore
    private Set<Student> students;

    @OneToMany(mappedBy = "faculty")
    @JsonIgnore
    private Set<ClassRoom> classes;

    public Set<ClassRoom> getClasses() {
        return classes;
    }

    public void setClasses(Set<ClassRoom> classes) {
        this.classes = classes;
    }

    @JsonValue
    public String toJson() {
        return this.name;
    }

    // Getter và Setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Staff> getStaff() {
        return staff;
    }

    public void setStaff(Set<Staff> staff) {
        this.staff = staff;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Faculty{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + '}';
    }
}
