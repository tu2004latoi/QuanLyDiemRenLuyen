/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Student;
import com.dtt.pojo.User;
import com.dtt.repositories.StudentRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
public class StudentRepositoryImpl implements StudentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Student addOrUpdateStudent(Student st) {
        Session s = this.factory.getObject().getCurrentSession();

        // Nếu Student có ID thì cập nhật
        if (st.getId() == null) {
            // Nếu User đã có ID (đã tồn tại trong cơ sở dữ liệu), sử dụng merge
            if (st.getUser() != null && st.getUser().getId() != null) {
                st.setUser((User) s.merge(st.getUser())); // Đảm bảo User được merge vào session
            }
            // Sau đó persist Student (có thể tạo mới hoặc cập nhật nếu đã có ID)
            s.persist(st);
        } else {
            // Nếu Student đã có ID, cập nhật (merge cả User và Student)
            if (st.getUser() != null && st.getUser().getId() != null) {
                st.setUser((User) s.merge(st.getUser())); // Đảm bảo User được merge vào session
            }
            s.merge(st); // Merge Student và các liên kết khác
        }

        return st;
    }

    @Override
    public long count() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Student", Student.class);

        return q.getResultCount();
    }

    @Override
    public Student getStudentByUserId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Student.findByUserId", Student.class);
        q.setParameter("userId", id);

        // Dùng getResultList() thay vì getSingleResult để tránh exception khi không tìm thấy kết quả
        List<Student> result = q.getResultList();

        // Nếu không tìm thấy, trả về null
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Student> getAllStudents() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Student", Student.class);

        return q.getResultList();
    }

    @Override
    public void deleteStudentById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Student st = this.getStudentByUserId(id);
        s.remove(st);
    }

}
