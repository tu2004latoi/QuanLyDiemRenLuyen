/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.repositories.impl;

import com.dtt.pojo.Comment;
import com.dtt.repositories.CommentRepository;
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
public class CommentRepositoryImpl implements CommentRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Comment addOrUpdateComment(Comment cmt) {
        Session s = this.factory.getObject().getCurrentSession();
        if (cmt.getId() == null) {
            s.persist(cmt);
        } else {
            s.merge(cmt);
        }

        return cmt;
    }

    @Override
    public long count() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Comment", Comment.class);

        return q.getResultCount();
    }

    @Override
    public Comment getCommentById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Comment.findById", Comment.class);
        q.setParameter("id", id);

        List<Comment> result = q.getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public Comment getCommentByUserIdAndActivityId(int userId, int activityId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("Comment.findByUserIdAndActivityId", Comment.class);
        q.setParameter("userId", userId);
        q.setParameter("activityId", activityId);

        List<Comment> result = q.getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<Comment> getCommentsByActivityId(int activityId) { //Lấy tất cả comment của 1 activity
    Session s = this.factory.getObject().getCurrentSession();
    Query<Comment> q = s.createQuery("FROM Comment c WHERE c.activity.id = :activityId", Comment.class);
    q.setParameter("activityId", activityId);

    return q.getResultList();
}

}
