/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Activity;
import static com.dtt.pojo.Activity.PointType.POINT_1;
import static com.dtt.pojo.Activity.PointType.POINT_2;
import static com.dtt.pojo.Activity.PointType.POINT_3;
import com.dtt.pojo.ActivityRegistrations;
import com.dtt.pojo.Attendance;
import com.dtt.pojo.User;
import com.dtt.services.ActivityRegistrationService;
import com.dtt.services.ActivityService;
import com.dtt.services.AttendanceService;
import com.dtt.services.UserService;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author MR TU
 */
@RestController
@RequestMapping("/api")
public class ApiAttendanceController {

    @Autowired
    private AttendanceService atSer;

    @Autowired
    private UserService userSer;

    @Autowired
    private ActivityService activitySer;

    @Autowired
    private ActivityRegistrationService arSer;

    @GetMapping("/attendances")
    public ResponseEntity<?> listAttendancesView() {
        List<Attendance> listAttendances = this.atSer.getAllAttendance();
        List<Map<String, Object>> listData = new ArrayList<>();

        for (Attendance at : listAttendances) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", at.getId());
            data.put("userId", at.getUser());
            data.put("activityId", at.getActivity());
            data.put("status", at.getStatus());
            data.put("timestamp", at.getTimestamp());
            data.put("is_register", at.getIsRegister());

            listData.add(data);
        }

        return ResponseEntity.ok(listData);
    }

    @PostMapping("/attendances/import")
    public ResponseEntity<?> importUserActivityCSV(@RequestParam("file") MultipartFile file) {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CSVFormat format = CSVFormat.Builder.create()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build();

            Iterable<CSVRecord> records = format.parse(reader);
            int successCount = 0;
            int failCount = 0;

            for (CSVRecord record : records) {
                try {
                    int userId = Integer.parseInt(record.get("userId"));
                    int activityId = Integer.parseInt(record.get("activityId"));
                    Attendance.Status status = Attendance.Status.valueOf(record.get("status").toUpperCase());
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.parse(record.get("timestamp"), formatter);

                    User user = userSer.getUserById(userId);
                    Activity activity = activitySer.getActivityById(activityId);
                    boolean registered = arSer.isRegistration(userId, activityId);

                    if (user != null && activity != null) {
                        Attendance at = new Attendance();
                        at.setUser(user);
                        at.setActivity(activity);
                        at.setStatus(status);
                        at.setTimestamp(localDateTime);
                        at.setIsRegister(!registered);
                        this.atSer.addAttendance(at);
                        successCount++;
                    } else {
                        failCount++;
                    }
                } catch (Exception e) {
                    failCount++;
                    // log exception if needed
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Import thành công!");
            response.put("successCount", successCount);
            response.put("failCount", failCount);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            ex.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Import thất bại: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @DeleteMapping("/attendances/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyAttendance(@PathVariable("id") int id) {
        Attendance at = this.atSer.getAttendanceById(id);
        this.atSer.deleteAttendance(at);
    }

    @DeleteMapping("/attendances/confirm")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroyAllAttendance() {
        List<Attendance> listAttendances = this.atSer.getAllAttendance();
        for (Attendance at : listAttendances) {
            if (!at.getIsRegister()) {
                this.atSer.deleteAttendance(at);
            } else {
                Activity a = this.activitySer.getActivityById(at.getActivity().getId());
                User u = this.userSer.getUserById(at.getUser().getId());
                Activity.PointType type = a.getPointType();
                switch (type) {
                    case POINT_1 ->
                        u.setPoint_1(u.getPoint_1() + a.getPointValue());
                    case POINT_2 ->
                        u.setPoint_2(u.getPoint_2() + a.getPointValue());
                    case POINT_3 ->
                        u.setPoint_3(u.getPoint_3() + a.getPointValue());
                    default ->
                        u.setPoint_4(u.getPoint_4() + a.getPointValue());
                }
                ActivityRegistrations ar = this.arSer.getActivityRegistrationByUserIdAndActivityId(u.getId(), a.getId());
                ar.setIsConfirm(true);
                this.arSer.addActivityRegistration(ar);
                this.userSer.updatePointUser(u);
                this.atSer.deleteAttendance(at);
            }
        }
    }
}
