/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.TrainingPoint;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MR TU
 */
@Repository
public interface TrainingPointRepository {
    TrainingPoint addOrUpdateTrainingPoint(TrainingPoint t);
    List<TrainingPoint> getAllTrainingPoints();
    TrainingPoint getTrainingPointById(int id);
    void deleteTrainingPointById(int id);
    List<TrainingPoint> getTrainingPoints(Map<String, String> params);
    TrainingPoint getTrainingPointByUserIdAndActivityId(int userId, int activityId);
}
