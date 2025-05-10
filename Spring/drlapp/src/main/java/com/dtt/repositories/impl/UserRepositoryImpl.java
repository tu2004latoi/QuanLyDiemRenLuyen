/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Email;
import com.dtt.pojo.Student;
import com.dtt.pojo.User;
import com.dtt.repositories.UserRepository;
import com.dtt.services.EmailService;
import com.dtt.services.StudentService;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MR TU
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private EmailService emailSer;

    @Autowired
    private StudentService stSer;

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", username);

        return (User) q.getSingleResult();
    }

    @Override
    public User register(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(u);
        s.refresh(u);
        return u;
    }

    @Override
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("User.findById", User.class);
        q.setParameter("id", id);

        try {
            return (User) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<User> getUsers(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root<User> root = q.from(User.class);
        q.select(root);

        if (params != null) {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            String firstName = params.get("firstName");
            if (firstName != null && !firstName.isEmpty()) {
                predicates.add(b.like(root.get("firstName"), String.format("%%%s%%", firstName)));
            }

            String lastName = params.get("lastName");
            if (lastName != null && !lastName.isEmpty()) {
                predicates.add(b.like(root.get("lastName"), String.format("%%%s%%", lastName)));
            }

            String email = params.get("email");
            if (email != null && !email.isEmpty()) {
                predicates.add(b.like(root.get("email"), String.format("%%%s%%", email)));
            }

            q.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        }

        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public User addOrUpdateUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        Email e = this.emailSer.getEmailByEmail(u.getEmail());

        // Kiểm tra xem email có hợp lệ không
        if (e == null) {
            throw new IllegalArgumentException("Email này chưa được cấp!");
        }

        // Nếu user chưa có ID (là đối tượng mới), sử dụng persist
        if (u.getId() == null) {
            s.persist(u);  // Persist cho đối tượng mới
        } else {
            s.merge(u);    // Merge cho đối tượng đã tồn tại hoặc bị tách rời
        }

        return u;
    }

    @Override
    public List<User> getAllUsers() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User", User.class);
        return q.getResultList();
    }

    @Override
    public void deleteUserById(int id) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            User u = this.getUserById(id);
            if (u != null) {
                if (u.getRole() == User.Role.STUDENT) {
                    Student student = u.getStudent();  // Giả sử có quan hệ giữa User và Student
                    if (student != null) {
                        s.remove(student);  // Xóa thông tin student trước khi xóa user
                    }
                }
                s.remove(u);
                System.out.println("Deleted activity with id: " + id); // Log cho kiểm tra
            } else {
                throw new RuntimeException("Activity not found with id: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error during delete: " + e.getMessage()); // Log lỗi
            throw e;
        }
    }

    @Override
    public long getCountUsers() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User", User.class);
        return q.getResultStream().count();
    }

    @Override
    public User updatePointUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.merge(u);

        return u;
    }

    @Override
    public List<User> getAllStudents() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("User.findAllStudent", User.class);
        q.setParameter("role", User.Role.STUDENT);

        return q.getResultList();
    }

}
