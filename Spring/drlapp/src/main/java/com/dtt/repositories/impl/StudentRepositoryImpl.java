/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Student;
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
public class StudentRepositoryImpl implements StudentRepository{
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Student addOrUpdateStudent(Student st) {
        Session s = this.factory.getObject().getCurrentSession();
        if (st.getId()==null){
            s.persist(st);
        } else {
            s.merge(st);
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
        q.setParameter("id", id);
        
        return (Student) q.getSingleResult();
    }

    @Override
    public List<Student> getAllStudents() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Student", Student.class);
        
        return q.getResultList();
    }
    
}
