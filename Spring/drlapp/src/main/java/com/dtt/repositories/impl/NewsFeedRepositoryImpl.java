/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.NewsFeed;
import com.dtt.repositories.NewsFeedRepository;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 
 * @author admin
 */
@Repository
@Transactional
public class NewsFeedRepositoryImpl implements NewsFeedRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

//    @Override
//    public NewsFeed getNewsFeedById(int id) {
//        Session s = this.factory.getObject().getCurrentSession();
//        Query q = s.createNamedQuery("NewsFeed.findById", NewsFeed.class);
//        q.setParameter("id", id);
//
//        try {
//            return (NewsFeed) q.getSingleResult();
//        } catch (NoResultException ex) {
//            return null;
//        }
//    }

//    @Override
//    public NewsFeed addNewsFeed(NewsFeed n) {
//        Session s = this.factory.getObject().getCurrentSession();
//        s.persist(n);
//        return n;
//    }

    @Override
    public List<NewsFeed> getAllNewsFeeds() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM NewsFeed", NewsFeed.class);
        return q.getResultList();
    }

//    @Override
//    public boolean deleteNewsFeed(int id) {
//        Session s = this.factory.getObject().getCurrentSession();
//        NewsFeed n = this.getNewsFeedById(id);
//        if (n != null) {
//            s.remove(n);
//            return true;
//        }
//        return false;
//    }
}
