/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Department;
import com.dtt.repositories.DepartmentRepository;
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
public class DepartmentRepositoryImpl implements DepartmentRepository{
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Department> getAllDepartments() {
        Session s =this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Department", Department.class);
        
        return q.getResultList();
    }

    @Override
    public Department getDepartmentById(int id) {
        Session s =this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Department.findById", Department.class);
        q.setParameter("id", id);
        
        return (Department) q.getSingleResult();
    }
    
}
