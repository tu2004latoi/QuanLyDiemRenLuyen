/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.ActivityRegistrations;
import com.dtt.pojo.Evidence;
import com.dtt.pojo.User;
import com.dtt.repositories.EvidenceRepository;
import com.dtt.repositories.MyActivityRepository;
import com.dtt.services.ActivityRegistrationService;
import com.dtt.services.EvidenceService;
import com.dtt.services.MyActivityService;
import com.dtt.services.UserService;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author MR TU
 */
@Repository
@Transactional
public class MyActivityRepositoryImpl implements MyActivityRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private UserService userSer;

    @Autowired
    private EvidenceService evidenceSer;

    @Autowired
    private EvidenceRepository evidenceRepo;
    
    @Autowired
    private ActivityRegistrationService arSer;

    @Override
    public List<ActivityRegistrations> getListMyActivities() {
        Session s = this.factory.getObject().getCurrentSession();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User u = this.userSer.getUserByUsername(username);
        Query q = s.createNamedQuery("ActivityRegistrations.findByUserId", ActivityRegistrations.class);
        q.setParameter("userId", u.getId());

        return q.getResultList();
    }

    @Override
    public void deleteMyActivityById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        ActivityRegistrations ar = this.arSer.getActivityRegistrationById(id);
        if (ar != null) {
            // Tìm Evidence theo ActivityRegistration
            Evidence e = this.evidenceRepo.getEvidenceByActivityRegistrationId(ar.getId());

            // Nếu tồn tại Evidence, tiến hành xóa
            if (e != null) {
                this.evidenceRepo.deleteEvidence(e.getId());
            }

            // Xóa ActivityRegistration
            s.remove(ar);
        } else {
            // Nếu không tìm thấy ActivityRegistration, thông báo lỗi hoặc xử lý thích hợp
            throw new IllegalArgumentException("ActivityRegistration with id " + id + " not found");
        }
    }

    @Override
    public ActivityRegistrations getMyActivityById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("ActivityRegistrations.findById", ActivityRegistrations.class);
        q.setParameter("id", id);

        return (ActivityRegistrations) q.getSingleResult();
    }

}
