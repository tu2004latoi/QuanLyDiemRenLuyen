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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @GetMapping("/activities/{activityId}/like")
    public List<Like> ActivityLikesView(@PathVariable("activityId") int activityId){
        return this.likeSer.getLikesByActivityId(activityId);
    }
    
    @PostMapping("/activities/{activityId}/like")
    public ResponseEntity<?> toggleLike(@PathVariable("activityId") int activityId, Principal principal) {
        // Lấy user hiện tại từ session
        User user = this.userSer.getUserByUsername(principal.getName());
        Activity activity = this.activitySer.getActivityWithLikes(activityId);

        if (user == null || activity == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy người dùng hoặc hoạt động.");
        }

        Like existingLike = this.likeSer.getLikeByUserIdAndActivityId(user.getId(), activityId);

        if (existingLike != null) {
            // Đã like -> bỏ like
            likeSer.deleteLike(existingLike);
        } else {
            // Chưa like -> tạo like
            Like like = new Like();
            like.setUser(user);
            like.setActivity(activity);
            like.setCreatedAt(LocalDateTime.now());
            this.likeSer.addOrUpdateLike(like);
        }

        // Đếm lại tổng like
        long likeCount = this.likeSer.countByActivityId(activityId);
        return ResponseEntity.ok(Map.of("likes", likeCount));
    }
}
