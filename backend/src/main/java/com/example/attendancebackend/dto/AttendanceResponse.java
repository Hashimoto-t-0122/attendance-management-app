package com.example.attendancebackend.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceResponse {

    private final Long id;
    private final Long employeeId;
    private final LocalDate workDate;
    private final LocalDateTime clockInTime;
    private final LocalDateTime clockOutTime;

    public AttendanceResponse(
            Long id,
            Long employeeId,
            LocalDate workDate,
            LocalDateTime clockInTime,
            LocalDateTime clockOutTime
    ) {
        this.id = id;
        this.employeeId = employeeId;
        this.workDate = workDate;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
    }

    public Long getId() {
        return id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public LocalDateTime getClockInTime() {
        return clockInTime;
    }

    public LocalDateTime getClockOutTime() {
        return clockOutTime;
    }
}