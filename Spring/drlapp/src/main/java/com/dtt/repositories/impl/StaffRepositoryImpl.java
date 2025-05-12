/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Staff;
import com.dtt.pojo.User;
import com.dtt.repositories.StaffRepository;
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
public class StaffRepositoryImpl implements StaffRepository{
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Staff getStaffByUserId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Staff.findByUserId", Staff.class);
        q.setParameter("userId", id);
        
        List<Staff> result = q.getResultList();
        
        return result.isEmpty() ? null : result.get(0);     
    }

    @Override
    public Staff addOrUpdateStaff(Staff staff) {
        Session s = this.factory.getObject().getCurrentSession();

        if (staff.getId() == null) {
            if (staff.getUser() != null && staff.getUser().getId() != null) {
                staff.setUser((User) s.merge(staff.getUser()));
            }
            s.persist(staff);
        } else {
            if (staff.getUser() != null && staff.getUser().getId() != null) {
                staff.setUser((User) s.merge(staff.getUser()));
            }
            s.merge(staff);
        }

        return staff;
    }
    
}
