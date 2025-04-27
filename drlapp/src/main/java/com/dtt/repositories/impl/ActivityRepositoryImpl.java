/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Activity;
import com.dtt.repositories.ActivityRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 
 * @author admin
 */
@Repository
@Transactional
public class ActivityRepositoryImpl implements ActivityRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

//    @Override
//    public Activity getActivityById(String id) {
//        Session s = this.factory.getObject().getCurrentSession();
//        Query q = s.createNamedQuery("Activity.findById", Activity.class);
//        q.setParameter("id", id);
//
//        try {
//            return (Activity) q.getSingleResult();
//        } catch (NoResultException ex) {
//            return null;
//        }
//    }

//    @Override
//    public Activity addActivity(Activity a) {
//        Session s = this.factory.getObject().getCurrentSession();
//        s.persist(a);
//        return a;
//    }

    @Override
    public List<Activity> getAllActivities() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Activity", Activity.class);
        return q.getResultList();
    }

//    @Override
//    public boolean deleteActivity(String id) {
//        Session s = this.factory.getObject().getCurrentSession();
//        Activity a = this.getActivityById(id);
//        if (a != null) {
//            s.remove(a);
//            return true;
//        }
//        return false;
//    }

//    @Override
//    public List<Activity> getActivitiesByStatus(String status) {
//        Session s = this.factory.getObject().getCurrentSession();
//        Query q = s.createQuery("FROM Activity a WHERE a.status = :status", Activity.class);
//        q.setParameter("status", status);
//        return q.getResultList();
//    }
}
