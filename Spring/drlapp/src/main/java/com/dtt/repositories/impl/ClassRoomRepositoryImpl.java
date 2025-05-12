/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.ClassRoom;
import com.dtt.repositories.ClassRoomRepository;
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
public class ClassRoomRepositoryImpl implements ClassRoomRepository{
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<ClassRoom> getAllClassRooms() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM ClassRoom", ClassRoom.class);
        
        return q.getResultList();
    }

    @Override
    public List<ClassRoom> getClassesByFacultyId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("ClassRoom.findByFacultyId", ClassRoom.class);
        q.setParameter("facultyId", id);
        
        return q.getResultList();
    }

    @Override
    public ClassRoom getClassRoomById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("ClassRoom.findById", ClassRoom.class);
        q.setParameter("id", id);
        
        return (ClassRoom) q.getSingleResult();
    }
    
}
