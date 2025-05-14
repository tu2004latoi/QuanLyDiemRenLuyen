/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Comment;
import com.dtt.repositories.CommentRepository;
import com.dtt.services.CommentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository cmtRepo;

    @Override
    public Comment addOrUpdateComment(Comment cmt) {
        return this.cmtRepo.addOrUpdateComment(cmt);
    }

    @Override
    public long count() {
        return this.cmtRepo.count();
    }

    @Override
    public Comment getCommentById(int id) {
        return this.cmtRepo.getCommentById(id);
    }

    @Override
    public Comment getCommentByUserIdAndActivityId(int userId, int activityId) {
        return this.cmtRepo.getCommentByUserIdAndActivityId(userId, activityId);
    }

    @Override
    public List<Comment> getCommentsByActivityId(int activityId) {
        return cmtRepo.getCommentsByActivityId(activityId);
    }
    
}
