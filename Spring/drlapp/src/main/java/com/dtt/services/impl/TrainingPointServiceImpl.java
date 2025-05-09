/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.services.impl;

import com.dtt.pojo.Activity;
import static com.dtt.pojo.Activity.PointType.POINT_1;
import static com.dtt.pojo.Activity.PointType.POINT_2;
import static com.dtt.pojo.Activity.PointType.POINT_3;
import com.dtt.pojo.Evidence;
import com.dtt.pojo.TrainingPoint;
import com.dtt.pojo.User;
import com.dtt.repositories.ActivityRepository;
import com.dtt.repositories.TrainingPointRepository;
import com.dtt.repositories.UserRepository;
import com.dtt.services.EvidenceService;
import com.dtt.services.TrainingPointService;
import com.dtt.services.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author MR TU
 */
@Service
public class TrainingPointServiceImpl implements TrainingPointService {

    @Autowired
    private TrainingPointRepository trainingPointRepo;

    @Autowired
    private ActivityRepository activityRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EvidenceService evidenceSer;

    @Autowired
    private UserService userSer;

    @Override
    public void registerToTrainingPoint(int userId, int activityId) {
        Activity a = activityRepo.getActivityById(activityId);
        User u = userRepo.getUserById(userId);

        TrainingPoint t = new TrainingPoint();
        t.setActivity(a);
        t.setUser(u);
        t.setPoint(a.getPointValue());
        t.setDateAwarded(LocalDateTime.now());
        t.setConfirmedBy(null);
        t.setStatus(TrainingPoint.Status.PENDING);

        trainingPointRepo.addOrUpdateTrainingPoint(t);
    }

    @Override
    public TrainingPoint addOrUpdateTrainingPoint(TrainingPoint t) {
        return this.trainingPointRepo.addOrUpdateTrainingPoint(t);
    }

    @Override
    public List<TrainingPoint> getAllTrainingPoints() {
        return this.trainingPointRepo.getAllTrainingPoints();
    }

    @Override
    public TrainingPoint getTrainingPointById(int id) {
        return this.trainingPointRepo.getTrainingPointById(id);
    }

    @Override
    public void deleteTrainingPointById(int id) {
        this.trainingPointRepo.deleteTrainingPointById(id);
    }

    @Override
    public List<TrainingPoint> getTrainingPoints(Map<String, String> params) {
        return this.trainingPointRepo.getTrainingPoints(params);
    }

    @Override
    public TrainingPoint getTrainingPointByUserIdAndActivityId(int userId, int activityId) {
        return this.trainingPointRepo.getTrainingPointByUserIdAndActivityId(userId, activityId);
    }

    @Override
    public void confirmTrainingPointById(int id, User confirmer) throws Exception {
        TrainingPoint t = this.getTrainingPointById(id);
        if (t == null) {
            throw new Exception("Training point not found");
        }

        t.setConfirmedBy(confirmer);
        t.setStatus(TrainingPoint.Status.CONFIRMED);

        Evidence e = this.evidenceSer.getEvidenceByTrainingPointId(id);
        if (e != null) {
            e.setVerifyStatus(Evidence.VerifyStatus.APPROVED);
        }

        Activity.PointType type = t.getActivity().getPointType();
        User addPointUser = t.getUser();
        int point = t.getPoint();
        switch (type) {
            case POINT_1 ->
                addPointUser.setPoint_1(addPointUser.getPoint_1() + point);
            case POINT_2 ->
                addPointUser.setPoint_2(addPointUser.getPoint_2() + point);
            case POINT_3 ->
                addPointUser.setPoint_3(addPointUser.getPoint_3() + point);
            default ->
                addPointUser.setPoint_4(addPointUser.getPoint_4() + point);
        }

        this.addOrUpdateTrainingPoint(t);
        if (e != null) {
            this.evidenceSer.addOrUpdateEvidence(e);
        }
        this.userSer.updatePointUser(addPointUser);
    }

    @Override
    public void rejectTrainingPointById(int id, User confirmer) throws Exception {
        TrainingPoint t = this.getTrainingPointById(id);
        if (t == null) {
            throw new Exception("Training point not found");
        }

        t.setConfirmedBy(confirmer);
        t.setStatus(TrainingPoint.Status.CONFIRMED);

        Evidence e = this.evidenceSer.getEvidenceByTrainingPointId(id);
        if (e != null) {
            e.setVerifyStatus(Evidence.VerifyStatus.REJECTED);
        }

        Activity.PointType type = t.getActivity().getPointType();
        User addPointUser = t.getUser();
        int point = t.getPoint();
        switch (type) {
            case POINT_1 ->
                addPointUser.setPoint_1(addPointUser.getPoint_1() - point);
            case POINT_2 ->
                addPointUser.setPoint_2(addPointUser.getPoint_2() - point);
            case POINT_3 ->
                addPointUser.setPoint_3(addPointUser.getPoint_3() - point);
            default ->
                addPointUser.setPoint_4(addPointUser.getPoint_4() - point);
        }

        this.addOrUpdateTrainingPoint(t);
        if (e != null) {
            this.evidenceSer.addOrUpdateEvidence(e);
        }
        this.userSer.updatePointUser(addPointUser);
    }

    @Override
    public void rejectAfterApprovedTrainingPointById(int id, User confirmer) throws Exception {
        TrainingPoint t = this.getTrainingPointById(id);
        if (t == null) {
            throw new Exception("Training point not found");
        }

        t.setStatus(TrainingPoint.Status.REJECTED);

        Evidence e = this.evidenceSer.getEvidenceByTrainingPointId(id);
        if (e != null) {
            e.setVerifyStatus(Evidence.VerifyStatus.REJECTED);
        }

        Activity.PointType type = t.getActivity().getPointType();
        User addPointUser = t.getUser();
        int point = t.getPoint();
        switch (type) {
            case POINT_1 ->
                addPointUser.setPoint_1(addPointUser.getPoint_1() - point);
            case POINT_2 ->
                addPointUser.setPoint_2(addPointUser.getPoint_2() - point);
            case POINT_3 ->
                addPointUser.setPoint_3(addPointUser.getPoint_3() - point);
            default ->
                addPointUser.setPoint_4(addPointUser.getPoint_4() - point);
        }

        this.addOrUpdateTrainingPoint(t);
        if (e != null) {
            this.evidenceSer.addOrUpdateEvidence(e);
        }
        this.userSer.updatePointUser(addPointUser);
    }
}
