/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Admin;
import com.dtt.pojo.User;
import com.dtt.repositories.AdminRepository;
import java.util.List;
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
public class AdminRepositoryImpl implements AdminRepository{
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Admin getAdminByUserId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Admin.findByUserId", Admin.class);
        q.setParameter("userId", id);
        
        List<Admin> result = q.getResultList();
        
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public Admin addOrUpdateAdmin(Admin ad) {
        Session s = this.factory.getObject().getCurrentSession();
        
        if (ad.getId() == null) {
            if (ad.getUser() != null && ad.getUser().getId() != null) {
                ad.setUser((User) s.merge(ad.getUser()));
            }
            s.persist(ad);
        } else {
            if (ad.getUser() != null && ad.getUser().getId() != null) {
                ad.setUser((User) s.merge(ad.getUser()));
            }
            s.merge(ad);
        }

        return ad;
    }
    
}
