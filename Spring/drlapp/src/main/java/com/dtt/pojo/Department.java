package com.dtt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "departments")
@NamedQueries({
    @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d"),
    @NamedQuery(name = "Department.findById", query = "SELECT d FROM Department d WHERE d.id = :id"),
    @NamedQuery(name = "Department.findByName", query = "SELECT d FROM Department d WHERE d.name = :name"),
    @NamedQuery(name = "Department.findByDescription", query = "SELECT d FROM Department d WHERE d.description = :description")
})
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;

    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private Set<Admin> admin;

    public Set<Admin> getAdmin() {
        return admin;
    }

    public void setAdmin(Set<Admin> admin) {
        this.admin = admin;
    }

    // Constructors
    public Department() {
    }

    public Department(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    // toString
    @Override
    public String toString() {
        return "Department{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + '}';
    }
}
