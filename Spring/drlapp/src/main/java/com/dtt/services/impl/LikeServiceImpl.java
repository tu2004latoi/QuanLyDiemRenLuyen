/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Like;
import com.dtt.repositories.LikeRepository;
import com.dtt.services.LikeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    private LikeRepository likeRepo;

    @Override
    public Like addOrUpdateLike(Like l) {
        return this.likeRepo.addOrUpdateLike(l);
    }

    @Override
    public long countByActivityId(int id) {
        return this.likeRepo.countByActivityId(id);
    }

    @Override
    public Like getLikeById(int id) {
        return this.likeRepo.getLikeById(id);
    }

    @Override
    public Like getLikeByUserIdAndActivityId(int userId, int activityId) {
        return this.likeRepo.getLikeByUserIdAndActivityId(userId, activityId);
    }

    @Override
    public void deleteLike(Like l) {
        this.likeRepo.deleteLike(l);
    }

    @Override
    public List<Like> getLikesByActivityId(int id) {
        return this.likeRepo.getLikesByActivityId(id);
    }
    
}
