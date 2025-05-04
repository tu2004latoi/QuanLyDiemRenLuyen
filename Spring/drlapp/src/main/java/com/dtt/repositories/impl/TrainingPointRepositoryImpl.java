/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Activity;
import com.dtt.pojo.TrainingPoint;
import com.dtt.pojo.User;
import com.dtt.repositories.TrainingPointRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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
public class TrainingPointRepositoryImpl implements TrainingPointRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public TrainingPoint addOrUpdateTrainingPoint(TrainingPoint t) {
        Session s = this.factory.getObject().getCurrentSession();
        if (t.getId() == null) {
            s.persist(t);
        } else {
            s.merge(t);
        }

        return t;
    }

    @Override
    public List<TrainingPoint> getAllTrainingPoints() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("TrainingPoint.findAll", TrainingPoint.class);
        return q.getResultList();
    }

    @Override
    public TrainingPoint getTrainingPointById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("TrainingPoint.findById", TrainingPoint.class);
        q.setParameter("id", id);

        try {
            return (TrainingPoint) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void deleteTrainingPointById(int id) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            TrainingPoint ar = this.getTrainingPointById(id);
            if (ar != null) {
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
    public List<TrainingPoint> getTrainingPoints(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<TrainingPoint> q = b.createQuery(TrainingPoint.class);
        Root<TrainingPoint> root = q.from(TrainingPoint.class);
        q.select(root);

        Join<TrainingPoint, User> userJoin = root.join("user", JoinType.LEFT);
        Join<TrainingPoint, Activity> activityJoin = root.join("activity", JoinType.LEFT);

        if (params != null) {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            String firstName = params.get("firstName");
            if (firstName != null && !firstName.isEmpty()) {
                predicates.add(b.like(userJoin.get("firstName"), String.format("%%%s%%", firstName)));
            }

            String lastName = params.get("lastName");
            if (lastName != null && !lastName.isEmpty()) {
                predicates.add(b.like(userJoin.get("lastName"), String.format("%%%s%%", lastName)));
            }

            String email = params.get("email");
            if (email != null && !email.isEmpty()) {
                predicates.add(b.like(userJoin.get("email"), String.format("%%%s%%", email)));
            }

            String activityName = params.get("activityName");
            if (activityName != null && !activityName.isEmpty()) {
                predicates.add(b.like(activityJoin.get("activityName"), String.format("%%%s%%", activityName)));
            }

            q.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        }

        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public TrainingPoint getTrainingPointByUserIdAndActivityId(int userId, int activityId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("TrainingPoint.findByUserIdAndActivityId", TrainingPoint.class);
        q.setParameter("userId", userId);
        q.setParameter("activityId", activityId);
        return (TrainingPoint) q.getSingleResult();
    }

}
