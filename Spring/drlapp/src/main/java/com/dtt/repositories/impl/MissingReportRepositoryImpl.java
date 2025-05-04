/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.MissingReport;
import com.dtt.repositories.MissingReportRepository;
import org.hibernate.Session;
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
public class MissingReportRepositoryImpl implements MissingReportRepository{
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
    
}
