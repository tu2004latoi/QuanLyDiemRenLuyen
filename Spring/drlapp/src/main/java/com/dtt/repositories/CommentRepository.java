/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.Comment;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MR TU
 */
@Repository
public interface CommentRepository {
    Comment addOrUpdateComment(Comment cmt);
    long count();
    Comment getCommentById(int id);
    Comment getCommentByUserIdAndActivityId(int userId, int activityId);
    List<Comment> getCommentsByActivityId(int activityId);
}
