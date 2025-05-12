package com.dtt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "class_room")
@NamedQueries({
    @NamedQuery(name = "ClassRoom.findAll", query = "SELECT c FROM ClassRoom c"),
    @NamedQuery(name = "ClassRoom.findById", query = "SELECT c FROM ClassRoom c WHERE c.id = :id"),
    @NamedQuery(name = "ClassRoom.findByName", query = "SELECT c FROM ClassRoom c WHERE c.name = :name"),
    @NamedQuery(name = "ClassRoom.findByFacultyId", query = "SELECT c FROM ClassRoom c WHERE c.faculty.id = :facultyId")
})
public class ClassRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_class_faculty"))
    private Faculty faculty;

    @OneToMany(mappedBy = "classRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Student> students;

    // Getters & Setters
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

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "ClassRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", faculty=" + (faculty != null ? faculty.getId() : null) +
                '}';
    }
}
