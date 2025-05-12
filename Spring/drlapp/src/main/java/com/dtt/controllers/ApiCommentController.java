/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Activity;
import com.dtt.pojo.Comment;
import com.dtt.pojo.User;
import com.dtt.services.ActivityService;
import com.dtt.services.CommentService;
import com.dtt.services.UserService;
import java.security.Principal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api")
public class ApiCommentController {
    @Autowired
    private UserService userSer;
    
    @Autowired
    private ActivityService activitySer;
    
    @Autowired
    private CommentService cmtSer;
    
    @PostMapping("/activities/{activityId}/comments")
    public String postComment(@PathVariable("activityId") int activityId,
                              @RequestParam("content") String content,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        User user = this.userSer.getUserByUsername(principal.getName());
        Activity activity = this.activitySer.getActivityWithComments(activityId);

        if (user != null && activity != null && !content.isBlank()) {
            Comment comment = new Comment();
            comment.setUser(user);
            comment.setActivity(activity);
            comment.setContent(content);
            comment.setCreatedAt(LocalDateTime.now());

            cmtSer.addOrUpdateComment(comment);
        }

        redirectAttributes.addFlashAttribute("success", "Bình luận đã được gửi!");
        return "redirect:/activities/" + activityId;
    }
}
