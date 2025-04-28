package com.dtt.pojo;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "admins")
@NamedQueries({
    @NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a"),
    @NamedQuery(name = "Admin.findById", query = "SELECT a FROM Admin a WHERE a.id = :id"),
    @NamedQuery(name = "Admin.findByDepartment", query = "SELECT a FROM Admin a WHERE a.department = :department"),
    @NamedQuery(name = "Admin.findByUser", query = "SELECT a FROM Admin a WHERE a.user = :user")
})
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

<<<<<<< HEAD
    // Getter và Setter
=======
    // Getters and setters
    /**
     * @return the id
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public String getId() {
        return id;
    }

<<<<<<< HEAD
=======
    /**
     * @param id the id to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setId(String id) {
        this.id = id;
    }

<<<<<<< HEAD
=======
    /**
     * @return the user
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public User getUser() {
        return user;
    }

<<<<<<< HEAD
=======
    /**
     * @param user the user to set
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public void setUser(User user) {
        this.user = user;
    }

<<<<<<< HEAD
=======
    /**
     * @return the department
     */
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
    public String getDepartment() {
        return department;
    }

<<<<<<< HEAD
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
=======
    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }
>>>>>>> cfa281a825bfbfa4e8f73cbf450eb84e9bc896b8
}
