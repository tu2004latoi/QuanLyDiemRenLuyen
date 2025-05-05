/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.ActivityRegistrations;
import com.dtt.pojo.Evidence;
import com.dtt.pojo.TrainingPoint;
import com.dtt.repositories.EvidenceRepository;
import com.dtt.services.TrainingPointService;
import jakarta.persistence.NoResultException;
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
public class EvidenceRepositoryImpl implements EvidenceRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private TrainingPointService tpSer;

    @Override
    public Evidence addOrUpdateEvidence(Evidence e) {
        Session s = this.factory.getObject().getCurrentSession();
        if (e.getId() == null) {
            s.persist(e);
        } else {
            s.merge(e);
        }

        return e;
    }

    @Override
    public void deleteEvidence(int id) {
        Session s = this.factory.getObject().getCurrentSession();

        Evidence e = s.get(Evidence.class, id); // đảm bảo lấy trong cùng session
        if (e != null) {
            TrainingPoint t = this.tpSer.getTrainingPointById(e.getId());
            if (t != null) {
                s.remove(t);
            }
            s.remove(e);
        }
    }

    @Override
    public Evidence getEvidenceByActivityRegistration(ActivityRegistrations ar) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Evidence.findByActivityRegistration", Evidence.class);
        q.setParameter("activityRegistration", ar);

        return (Evidence) q.getSingleResult();
    }

    @Override
    public Evidence getEvidenceById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Evidence.findById", Evidence.class);
        q.setParameter("id", id);

        return (Evidence) q.getSingleResult();
    }

    @Override
    public Evidence getEvidenceByActivityRegistrationId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            Query q = s.createNamedQuery("Evidence.findByActivityRegistrationId", Evidence.class);
            q.setParameter("activityRegistrationId", id);

            return (Evidence) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Evidence getEvidenceByTrainingPointId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Evidence.findByTrainingPointId", Evidence.class);
        q.setParameter("trainingPointId", id);
        
        return (Evidence) q.getSingleResult();
    }

}
