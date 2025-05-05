/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Activity;
import com.dtt.pojo.ActivityRegistrations;
import com.dtt.pojo.Evidence;
import com.dtt.pojo.TrainingPoint;
import com.dtt.repositories.ActivityRegistrationRepository;
import com.dtt.services.ActivityService;
import com.dtt.services.EvidenceService;
import com.dtt.services.TrainingPointService;
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
public class ActivityRegistrationRepositoryImpl implements ActivityRegistrationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Autowired
    private EvidenceService evidenceSer;
    
    @Autowired
    private TrainingPointService tpSer;
    
    @Autowired
    private ActivityService activitySer;

    @Override
    public ActivityRegistrations addActivityRegistration(ActivityRegistrations ar) {
        Session s = this.factory.getObject().getCurrentSession();
        if (ar.getId() == null) {
            s.persist(ar);
        } else {
            s.merge(ar);
        }
        
        return ar;
    }

    @Override
    public boolean isRegistration(int userId, int activityId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("ActivityRegistrations.findByUserAndActivity", ActivityRegistrations.class);
        q.setParameter("userId", userId);
        q.setParameter("activityId", activityId);

        return q.getResultList().isEmpty();
    }

    @Override
    public List<ActivityRegistrations> getAllActivityRegistrations() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("ActivityRegistrations.findAll", ActivityRegistrations.class);
        return q.getResultList();
    }

    @Override
    public void deleteActivityRegistrationById(int id) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            ActivityRegistrations ar = this.getActivityRegistrationById(id);
            if (ar != null) {
                Evidence e = this.evidenceSer.getEvidenceByActivityRegistrationId(id);
                if (e!=null){
                    TrainingPoint t = e.getTrainingPoint();
                    if (t!=null){
                        s.remove(t);
                    }
                    s.remove(e);
                }
                Activity a = this.activitySer.getActivityById(ar.getActivity().getId());
                a.setCurrentParticipants(a.getCurrentParticipants()-1);
                this.activitySer.addOrUpdateActivity(a);
                s.remove(ar);
                System.out.println("Deleted AR with id: " + id); // Log cho kiểm tra
            } else {
                throw new RuntimeException("AR not found with id: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error during delete: " + e.getMessage()); // Log lỗi
            throw e;
        }
    }

    @Override
    public ActivityRegistrations getActivityRegistrationById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("ActivityRegistrations.findById", ActivityRegistrations.class);
        q.setParameter("id", id);

        try {
            return (ActivityRegistrations) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

}
