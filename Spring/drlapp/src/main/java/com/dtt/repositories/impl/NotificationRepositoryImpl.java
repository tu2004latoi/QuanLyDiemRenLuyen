/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Notification;
import com.dtt.pojo.User;
import com.dtt.repositories.NotificationRepository;
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
public class NotificationRepositoryImpl implements NotificationRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Notification> listNotifications() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Notification", Notification.class);

        return q.getResultList();
    }

    @Override
    public Notification getNotificationById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Notification.findById", Notification.class);
        q.setParameter("id", id);

        return (Notification) q.getSingleResult();
    }

    @Override
    public List<Notification> getNotificationsByUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Notification.findByUserId", Notification.class);
        q.setParameter("userId", u.getId());

        return q.getResultList();
    }

    @Override
    public void deleteNotification(Notification n) {
        Session s = this.factory.getObject().getCurrentSession();
        if (n != null) {
            s.remove(n);
        }
    }

    @Override
    public Long size() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Notification", Notification.class);

        return q.getResultCount();
    }

    @Override
    public Notification addNotification(Notification n) {
        Session s = this.factory.getObject().getCurrentSession();
        if (n != null) {
            s.persist(n);
        }

        return n;
    }

    @Override
    public List<Notification> listNotReadByUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Notification.findByUserIdNotRead", Notification.class);
        q.setParameter("userId", u.getId());

        return q.getResultList();
    }

    @Override
    public void markAsRead(Notification n) {
        Session s = this.factory.getObject().getCurrentSession();
        if (n != null) {
            n.setIsRead(true);
            s.merge(n);
        }
    }

    @Override
    public void markAllAsRead(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Notification.findByUserIdNotRead", Notification.class);
        q.setParameter("userId", u.getId());

        List<Notification> unreadNotifications = q.getResultList();
        for (Notification n : unreadNotifications) {
            n.setIsRead(true);
            s.merge(n);
        }
    }

    @Override
    public long countUnreadByUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Notification.findByUserIdNotRead", Notification.class);
        q.setParameter("userId", u.getId());

        return q.getResultCount();
    }

}
