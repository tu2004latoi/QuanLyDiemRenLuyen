/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Faculty;
import com.dtt.repositories.FacultyRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.util.List;
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
public class FacultyRepositoryImpl implements FacultyRepository{
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Faculty getFacultyById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Faculty.findById", Faculty.class);
        q.setParameter("id", id);

        try {
            return (Faculty) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Faculty addOrUpdateFaculty(Faculty f) {
        Session s = this.factory.getObject().getCurrentSession();
        if (f.getId()==null){
            s.persist(f);
        } else {
            s.merge(f);
        }
        
        return f;
    }
    @Override
    public List<Faculty> getAllFaculties() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Faculty", Faculty.class);
        return q.getResultList();
    }

    @Override
    public void deleteFaculty(int id) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Faculty f = this.getFacultyById(id);
            if (f != null) {
                s.remove(f);
                System.out.println("Deleted faculty with id: " + id); // Log cho kiểm tra
            } else {
                throw new RuntimeException("Faculty not found with id: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error during delete: " + e.getMessage()); // Log lỗi
            throw e;
        }
    }

    @Override
    public Faculty getFacultyByName(String name) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Faculty.findByName", Faculty.class);
        q.setParameter("name", name);
        
        return (Faculty) q.getSingleResult();
    }
}
