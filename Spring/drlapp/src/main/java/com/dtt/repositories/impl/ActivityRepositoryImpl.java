/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Activity;
import com.dtt.pojo.ActivityRegistrations;
import com.dtt.repositories.ActivityRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashSet;
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
    public Activity getActivityById(int id) {
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
        if (a.getId() == null) {
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
    public void deleteActivity(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Activity a = this.getActivityById(id);
        if (a != null) {
            a.getRegistrations().clear();
            s.flush();
            s.remove(a);
        } else {
            throw new RuntimeException("Activity not found with id: " + id);
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

            // Lọc theo khoa
            String facultyId = params.get("facultyId");
            if (facultyId != null && !facultyId.isEmpty()) {
                predicates.add(b.equal(root.get("faculty").get("id"), Integer.parseInt(facultyId)));
            }

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
            }

            String fromPoint = params.get("fromPoint");
            if (fromPoint != null && !fromPoint.isEmpty()) {
                int from = Integer.parseInt(fromPoint);
                predicates.add(b.greaterThanOrEqualTo(root.get("pointValue"), from));
            }

            String toPoint = params.get("toPoint");
            if (toPoint != null && !toPoint.isEmpty()) {
                int to = Integer.parseInt(toPoint);
                predicates.add(b.lessThanOrEqualTo(root.get("pointValue"), to));

            }

            q.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        }

        Query query = s.createQuery(q);

        if (params != null) {
            int page = Integer.parseInt(params.getOrDefault("page", "1"));
            int size = Integer.parseInt(params.getOrDefault("size", "6"));
            int start = (page - 1) * size;

            query.setMaxResults(size);
            query.setFirstResult(start);
        }

        return query.getResultList();
    }

    @Override
    public long getCountActivities() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Activity", Activity.class);

        return q.getResultStream().count();
    }

    @Override
    public long count(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Activity> root = query.from(Activity.class);

        // Chỉ đếm số lượng bản ghi
        query.select(builder.count(root));

        // Nếu có tham số tìm kiếm, áp dụng điều kiện lọc
        if (params != null) {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            // Lọc theo khoa
            String facultyId = params.get("facultyId");
            if (facultyId != null && !facultyId.isEmpty()) {
                predicates.add(builder.equal(root.get("faculty").get("id"), Integer.parseInt(facultyId)));
            }

            // Lọc theo từ khóa (tên hoạt động)
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(builder.like(root.get("name"), "%" + kw + "%"));
            }

            // Lọc theo điểm min
            String fromPoint = params.get("fromPoint");
            if (fromPoint != null && !fromPoint.isEmpty()) {
                int from = Integer.parseInt(fromPoint);
                predicates.add(builder.greaterThanOrEqualTo(root.get("pointValue"), from));
            }

            // Lọc theo điểm max
            String toPoint = params.get("toPoint");
            if (toPoint != null && !toPoint.isEmpty()) {
                int to = Integer.parseInt(toPoint);
                predicates.add(builder.lessThanOrEqualTo(root.get("pointValue"), to));
            }

            // Áp dụng tất cả các điều kiện lọc
            query.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        }

        // Thực hiện truy vấn và trả về số lượng kết quả
        return s.createQuery(query).getSingleResult();

    }

}
