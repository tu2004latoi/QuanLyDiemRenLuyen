package com.dtt.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "admins")
@NamedQueries({
    @NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a"),
    @NamedQuery(name = "Admin.findById", query = "SELECT a FROM Admin a WHERE a.id = :id"),
    @NamedQuery(name = "Admin.findByDepartmentId", query = "SELECT a FROM Admin a WHERE a.department.id = :departmentId"),
    @NamedQuery(name = "Admin.findByUserId", query = "SELECT a FROM Admin a WHERE a.user.id = :userId")
})
public class Admin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

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

    @Override
    public String toString() {
        return "Admin{"
                + "id='" + id + '\''
                + ", department='" + department + '\''
                + ", user=" + user
                + '}';
    }
}
