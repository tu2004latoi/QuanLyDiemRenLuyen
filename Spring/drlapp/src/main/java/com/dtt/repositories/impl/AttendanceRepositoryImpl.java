/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Attendance;
import com.dtt.repositories.AttendanceRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class AttendanceRepositoryImpl implements AttendanceRepository{
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Attendance addAttendance(Attendance at) {
        Session s = this.factory.getObject().getCurrentSession();
        if (at!=null){
            s.persist(at);
        }
        
        return at;
    }

    @Override
    public List<Attendance> getAllAttendance() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Attendance", Attendance.class);
        
        return q.getResultList();
    }

    @Override
    public void deleteAttendance(Attendance at) {
        Session s = this.factory.getObject().getCurrentSession();
        if (at!=null){
            s.remove(at);
        }
    }

    @Override
    public Attendance getAttendanceById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Attendance.findById", Attendance.class);
        q.setParameter("id", id);
        
        return (Attendance)q.getSingleResult();
    }
    
}
