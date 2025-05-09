/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Email;
import com.dtt.repositories.EmailRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
public class EmailRepositoryImpl implements EmailRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Email getEmailByEmail(String email) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            Query q = s.createNamedQuery("Email.findByEmail", Email.class);
            q.setParameter("email", email);

            return (Email) q.getSingleResult();
        } catch (NoResultException e){
            return null;
        }

    }

    @Override
    public List<Email> getEmails(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Email> q = b.createQuery(Email.class);
        Root<Email> root = q.from(Email.class);
        q.select(root);

        if (params != null) {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            String email = params.get("email");
            if (email != null && !email.isEmpty()) {
                predicates.add(b.like(root.get("email"), String.format("%%%s%%", email)));
            }

            q.where(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        }

        Query query = s.createQuery(q);

        return query.getResultList();

    }

    @Override
    public void deleteEmail(Email e) {
        Session s = this.factory.getObject().getCurrentSession();
        s.remove(e);
    }

    @Override
    public Email addEmail(Email e) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(e);

        return e;
    }

    @Override
    public long getCountEmails() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Email", Email.class);
        return q.getResultCount();
    }

}
