/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dtt.controllers;

import com.dtt.pojo.Activity;
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
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author MR TU
 */
@Controller
public class AttendanceController {

    @Autowired
    private UserService userSer;

    @Autowired
    private ActivityService activitySer;

    @Autowired
    private ActivityRegistrationService arSer;

    @Autowired
    private AttendanceService atSer;

    @PostMapping("/attendances/import")
    public String importUserActivityCSV(@RequestParam("file") MultipartFile file, Model model) {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CSVFormat format = CSVFormat.Builder.create()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build();

            Iterable<CSVRecord> records = format.parse(reader);

            for (CSVRecord record : records) {
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
                }
            }

            model.addAttribute("message", "Import thành công!");
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("error", "Import thất bại: " + ex.getMessage());
        }

        return "redirect:/attendances";
    }

    @GetMapping("/attendances")
    public String attendancesView(Model model) {

        model.addAttribute("attendance", this.atSer.getAllAttendance());
        return "attendances";
    }
}
