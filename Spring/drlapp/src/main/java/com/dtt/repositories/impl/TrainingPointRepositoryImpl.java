/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.TrainingPoint;
import com.dtt.repositories.TrainingPointRepository;
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
public class TrainingPointRepositoryImpl implements TrainingPointRepository{
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public TrainingPoint addOrUpdateTrainingPoint(TrainingPoint t) {
        Session s = this.factory.getObject().getCurrentSession();
        if (t.getId()==null){
            s.persist(t);
        } else {
            s.merge(t);
        }
        
        return t;
    }
    
}
