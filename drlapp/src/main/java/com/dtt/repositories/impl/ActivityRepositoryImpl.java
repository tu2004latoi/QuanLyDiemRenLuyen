/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Activity;
import com.dtt.repositories.ActivityRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 *
 * @author admin
 */
@Repository
@Transactional
public class ActivityRepositoryImpl implements ActivityRepository {

    private static final int PAGE_SIZE = 6;
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Activity getActivityById(String id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Activity.findById", Activity.class);
        q.setParameter("id", id);

        try {
            return (Activity) q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public Activity addOrUpdateActivity(Activity a) {
        Session s = this.factory.getObject().getCurrentSession();
        if (a.getId()==null){
            s.persist(a);
        } else {
            s.merge(a);
        }
        
        return a;
    }
    @Override
    public List<Activity> getAllActivities() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Activity", Activity.class);
        return q.getResultList();
    }

    @Override
    public void deleteActivity(String id) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Activity a = this.getActivityById(id);
            if (a != null) {
                s.remove(a);
                System.out.println("Deleted activity with id: " + id); // Log cho kiểm tra
            } else {
                throw new RuntimeException("Activity not found with id: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error during delete: " + e.getMessage()); // Log lỗi
            throw e;
        }
    }

//    @Override
//    public List<Activity> getActivitiesByStatus(String status) {
//        Session s = this.factory.getObject().getCurrentSession();
//        Query q = s.createQuery("FROM Activity a WHERE a.status = :status", Activity.class);
//        q.setParameter("status", status);
//        return q.getResultList();
//    }
    @Override
    public List<Activity> getActivities(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Activity> q = b.createQuery(Activity.class);
        Root<Activity> root = q.from(Activity.class);
        q.select(root);

        if (params != null) {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
            }

            String fromPoint = params.get("fromPoint");
            if (fromPoint != null && !fromPoint.isEmpty()) {
                predicates.add(b.greaterThanOrEqualTo(root.get("point_value"), fromPoint));
            }

            String toPoint = params.get("toPoint");
            if (toPoint != null && !toPoint.isEmpty()) {
                predicates.add(b.lessThanOrEqualTo(root.get("point_value"), toPoint));
            }

            q.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        }

        Query query = s.createQuery(q);

        if (params != null && params.containsKey("page")) {
            int page = Integer.parseInt(params.get("page"));
            int start = (page - 1) * PAGE_SIZE;

            query.setMaxResults(PAGE_SIZE);
            query.setFirstResult(start);
        }

        return query.getResultList();
    }

}
