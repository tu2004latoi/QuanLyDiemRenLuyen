/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Student;
import com.dtt.repositories.StatisticsRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MR TU
 */
@Controller
@Transactional
public class StatisticsRepositoryImpl implements StatisticsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Student> getStatistics(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Student> q = b.createQuery(Student.class);
        Root<Student> root = q.from(Student.class);
        q.select(root);

        if (params != null) {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            // Tìm theo khoa
            String facultyId = params.get("faculty");
            if (facultyId != null && !facultyId.isEmpty()) {
                predicates.add(b.equal(root.get("faculty").get("id"), Integer.parseInt(facultyId)));
            }

            // Tìm theo lớp
            String className = params.get("className");
            if (className != null && !className.isEmpty()) {
                predicates.add(b.equal(root.get("classRoom").get("id"), Integer.parseInt(className)));
            }

            q.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        }

        Query query = s.createQuery(q);
        List<Student> result = query.getResultList();

        // Tìm theo classification (lọc sau)
        String classification = params.get("classification");
        if (classification != null && !classification.isEmpty()) {
            result = result.stream()
                    .filter(sv -> classification.equals(sv.getClassify()))
                    .toList();
        }

        return result;
    }

}
