/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.MissingReport;
import com.dtt.repositories.MissingReportRepository;
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
public class MissingReportRepositoryImpl implements MissingReportRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public MissingReport addOrUpdateMissingReport(MissingReport mr) {
        Session s = this.factory.getObject().getCurrentSession();
        if (mr.getId() == null) {
            s.persist(mr);
        } else {
            s.merge(mr);
        }

        return mr;
    }

    @Override
    public List<MissingReport> getMissingReports(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<MissingReport> q = b.createQuery(MissingReport.class);
        Root<MissingReport> root = q.from(MissingReport.class);
        q.select(root);

        if (params != null) {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            // Join để truy cập thông tin user, student và department
            Join<?, ?> userJoin = root.join("user", JoinType.LEFT);
            Join<?, ?> studentJoin = userJoin.join("student", JoinType.LEFT);
            Join<?, ?> facultyJoin = studentJoin.join("faculty", JoinType.LEFT);

            String nameUser = params.get("nameUser");
            if (nameUser != null && !nameUser.isEmpty()) {
                predicates.add(b.like(userJoin.get("name"), String.format("%%%s%%", nameUser)));
            }

            String nameActivity = params.get("nameActivity");
            if (nameActivity != null && !nameActivity.isEmpty()) {
                predicates.add(b.like(root.get("activity").get("name"), String.format("%%%s%%", nameActivity)));
            }

            String fromPoint = params.get("fromPoint");
            if (fromPoint != null && !fromPoint.isEmpty()) {
                try {
                    predicates.add(b.greaterThanOrEqualTo(root.get("point"), Float.parseFloat(fromPoint)));
                } catch (NumberFormatException e) {
                    // ignore
                }
            }

            String toPoint = params.get("toPoint");
            if (toPoint != null && !toPoint.isEmpty()) {
                try {
                    predicates.add(b.lessThanOrEqualTo(root.get("point"), Float.parseFloat(toPoint)));
                } catch (NumberFormatException e) {
                    // ignore
                }
            }

            String facultyName = params.get("faculty");
            if (facultyName != null && !facultyName.isEmpty()) {
                predicates.add(b.equal(facultyJoin.get("name"), facultyName));
            }

            q.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        }

        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public MissingReport getMissingReportById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("MissingReport.findById", MissingReport.class);
        q.setParameter("id", id);

        return (MissingReport) q.getSingleResult();
    }

}
