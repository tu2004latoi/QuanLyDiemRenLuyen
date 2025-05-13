/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.ClassRoom;
import com.dtt.pojo.Faculty;
import com.dtt.repositories.ClassRoomRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
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
public class ClassRoomRepositoryImpl implements ClassRoomRepository {

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

    @Override
    public List<ClassRoom> getClasses(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<ClassRoom> q = b.createQuery(ClassRoom.class);
        Root<ClassRoom> root = q.from(ClassRoom.class);
        q.select(root);

        if (params != null) {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            String classroom = params.get("className");
            if (classroom != null && !classroom.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", classroom)));
            }

            String faculty = params.get("facultyName");
            if (faculty != null && !faculty.isEmpty()) {
                Join<ClassRoom, Faculty> facultyJoin = root.join("faculty");
                predicates.add(b.like(facultyJoin.get("name"), String.format("%%%s%%", faculty)));
            }

            q.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        }

        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public void deleteClassroom(ClassRoom c) {
        Session s = this.factory.getObject().getCurrentSession();
        s.remove(c);
    }

    @Override
    public ClassRoom addOrUpdate(ClassRoom c) {
        Session s = this.factory.getObject().getCurrentSession();
        if (c != null) {
            s.persist(c);
        } else {
            s.merge(c);
        }

        return c;
    }

}
