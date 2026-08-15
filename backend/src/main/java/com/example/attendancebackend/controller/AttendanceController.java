package com.example.attendancebackend.controller;

import java.util.List;
import com.example.attendancebackend.dto.AttendanceResponse;
import com.example.attendancebackend.service.AttendanceService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin(origins = "http://localhost:5173")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(
            AttendanceService attendanceService
    ) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/clock-in")
    public AttendanceResponse clockIn(
            @RequestParam Long employeeId
    ) {
        return attendanceService.clockIn(employeeId);
    }

    @PostMapping("/clock-out")
    public AttendanceResponse clockOut(
            @RequestParam Long employeeId
    ) {
        return attendanceService.clockOut(employeeId);
    }

    @GetMapping("/history")
    public List<AttendanceResponse> getHistory(
            @RequestParam Long employeeId
    ) {
        return attendanceService.getHistory(employeeId);
    }
}