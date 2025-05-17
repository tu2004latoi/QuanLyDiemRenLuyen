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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<?> postComment(@PathVariable("activityId") int activityId,
            @RequestBody Map<String, String> body,
            Principal principal) {
        String content = body.get("content");

        User user = this.userSer.getUserByUsername(principal.getName());
        Activity activity = this.activitySer.getActivityWithComments(activityId);

        if (user == null || activity == null || content == null || content.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Dữ liệu không hợp lệ!"));
        }

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setActivity(activity);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        cmtSer.addOrUpdateComment(comment);

        return ResponseEntity.ok(Map.of("message", "Bình luận đã được gửi!"));
    }

    @GetMapping("/activities/{id}/comments")
    public ResponseEntity<?> getCommentsForActivity(@PathVariable("id") int activityId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        List<Comment> comments = cmtSer.getCommentsByActivityId(activityId);
        List<Map<String, Object>> listData = new ArrayList<>();

        for (Comment c : comments) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", c.getId());
            data.put("userId", c.getUser().getId());
            data.put("userName", c.getUser().getName());
            data.put("userAvatar", c.getUser().getAvatar());
            data.put("activityId", c.getActivity().getId());
            data.put("content", c.getContent());
            data.put("createdAt", c.getCreatedAt().format(formatter));

            listData.add(data);
        }
        return ResponseEntity.ok(listData);
    }
}
