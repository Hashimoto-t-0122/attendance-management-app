package com.example.attendancebackend.service;

import java.util.List;
import com.example.attendancebackend.dto.AttendanceResponse;
import com.example.attendancebackend.entity.AttendanceRecord;
import com.example.attendancebackend.entity.Employee;
import com.example.attendancebackend.repository.AttendanceRepository;
import com.example.attendancebackend.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            EmployeeRepository employeeRepository
    ) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    public AttendanceResponse clockIn(Long employeeId) {

        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "社員が見つかりません"
                        )
                );

        LocalDate today = LocalDate.now();

        boolean alreadyClockedIn = attendanceRepository
                .findByEmployeeAndWorkDate(employee, today)
                .isPresent();

        if (alreadyClockedIn) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "本日はすでに出勤しています"
            );
        }

        AttendanceRecord record = new AttendanceRecord();

        record.setEmployee(employee);
        record.setWorkDate(today);
        record.setClockInTime(LocalDateTime.now());

        AttendanceRecord savedRecord = attendanceRepository.save(record);

        return new AttendanceResponse(
                savedRecord.getId(),
                savedRecord.getEmployee().getId(),
                savedRecord.getWorkDate(),
                savedRecord.getClockInTime(),
                savedRecord.getClockOutTime()
        );
    }
    public AttendanceResponse clockOut(Long employeeId) {

        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "社員が見つかりません"
                        )
                );

        LocalDate today = LocalDate.now();

        AttendanceRecord record = attendanceRepository
                .findByEmployeeAndWorkDate(employee, today)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "本日の出勤記録がありません"
                        )
                );

        if (record.getClockOutTime() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "本日はすでに退勤しています"
            );
        }

        record.setClockOutTime(LocalDateTime.now());

        AttendanceRecord savedRecord =
                attendanceRepository.save(record);

        return new AttendanceResponse(
                savedRecord.getId(),
                savedRecord.getEmployee().getId(),
                savedRecord.getWorkDate(),
                savedRecord.getClockInTime(),
                savedRecord.getClockOutTime()
        );
    }
    public List<AttendanceResponse> getHistory(Long employeeId) {

        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "社員が見つかりません"
                        )
                );

        return attendanceRepository
                .findByEmployeeOrderByWorkDateDesc(employee)
                .stream()
                .map(record ->
                        new AttendanceResponse(
                                record.getId(),
                                record.getEmployee().getId(),
                                record.getWorkDate(),
                                record.getClockInTime(),
                                record.getClockOutTime()
                        )
                )
                .toList();
    }
}