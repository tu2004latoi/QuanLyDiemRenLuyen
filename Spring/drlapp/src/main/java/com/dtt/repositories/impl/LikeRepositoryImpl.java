/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Like;
import com.dtt.repositories.LikeRepository;
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
public class LikeRepositoryImpl implements LikeRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Like addOrUpdateLike(Like l) {
        Session s = this.factory.getObject().getCurrentSession();
        if (l.getId()==null){
            s.persist(l);
        } else {
            s.merge(l);
        }
        
        return l;
    }

    @Override
    public long countByActivityId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Like.findByActivityId", Like.class);
        q.setParameter("activityId", id);

        return q.getResultCount();
    }

    @Override
    public Like getLikeById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Like.findById", Like.class);
        q.setParameter("id", id);
        
        List<Like> result = q.getResultList();
        
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public Like getLikeByUserIdAndActivityId(int userId, int activityId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Like.findByUserIdAndActivityId", Like.class);
        q.setParameter("userId", userId);
        q.setParameter("activityId", activityId);
        
        List<Like> result = q.getResultList();
        
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public void deleteLike(Like l) {
        Session s = this.factory.getObject().getCurrentSession();
        s.remove(l);
    }

    @Override
    public List<Like> getLikesByActivityId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Like.findByActivityId", Like.class);
        q.setParameter("activityId", id);
        
        return q.getResultList();
    }

}
