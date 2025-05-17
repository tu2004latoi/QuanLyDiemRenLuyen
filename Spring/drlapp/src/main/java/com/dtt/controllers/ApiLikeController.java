/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Activity;
import com.dtt.pojo.Like;
import com.dtt.pojo.User;
import com.dtt.services.ActivityService;
import com.dtt.services.CommentService;
import com.dtt.services.LikeService;
import com.dtt.services.UserService;
import jakarta.ejb.Local;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api")
public class ApiLikeController {

    @Autowired
    private UserService userSer;

    @Autowired
    private ActivityService activitySer;

    @Autowired
    private LikeService likeSer;

    @GetMapping("/activities/{activityId}/likes")
    public ResponseEntity<?> ActivityLikesView(@PathVariable("activityId") int activityId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        List<Like> likes = this.likeSer.getLikesByActivityId(activityId);
        List<Map<String, Object>> listData = new ArrayList<>();

        for (Like l : likes) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", l.getId());
            data.put("userId", l.getUser().getId());
            data.put("activityId", l.getActivity().getId());
            data.put("createAt", l.getCreatedAt().format(formatter));

            listData.add(data);
        }
        return ResponseEntity.ok(listData);
    }

    @GetMapping("/activities/{id}/likes/count") //Đếm tổng like của 1 activity
    public ResponseEntity<Long> countLikesForActivity(@PathVariable("id") int activityId) {
        long count = likeSer.countByActivityId(activityId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PostMapping("/activities/{activityId}/likes")
    public ResponseEntity<?> toggleLike(@PathVariable("activityId") int activityId, Principal principal) {
        System.out.println("Principal: " + (principal != null ? principal.getName() : "null"));
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Chưa đăng nhập");
        }
        User user = this.userSer.getUserByUsername(principal.getName());
        if (user == null) {
            return ResponseEntity.badRequest().body("Người dùng không tồn tại");
        }
        Activity activity = this.activitySer.getActivityWithLikes(activityId);
        if (activity == null) {
            return ResponseEntity.badRequest().body("Hoạt động không tồn tại");
        }
        Like existingLike = this.likeSer.getLikeByUserIdAndActivityId(user.getId(), activityId);
        try {
            if (existingLike != null) {
                likeSer.deleteLike(existingLike);
            } else {
                Like like = new Like();
                like.setUser(user);
                like.setActivity(activity);
                like.setCreatedAt(LocalDateTime.now());
                this.likeSer.addOrUpdateLike(like);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi server khi xử lý like");
        }
        long likeCount = this.likeSer.countByActivityId(activityId);
        return ResponseEntity.ok(Map.of("likes", likeCount));
    }

}
