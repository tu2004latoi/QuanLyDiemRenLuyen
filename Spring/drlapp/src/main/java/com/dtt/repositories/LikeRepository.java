/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.Like;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MR TU
 */
@Repository
public interface LikeRepository {
    Like addOrUpdateLike(Like l);
    long countByActivityId(int id);
    Like getLikeById(int id);
    Like getLikeByUserIdAndActivityId(int userId, int activityId);
    List<Like> getLikesByActivityId(int id);
    void deleteLike(Like l);
}
