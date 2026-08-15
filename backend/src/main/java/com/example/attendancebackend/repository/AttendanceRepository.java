package com.example.attendancebackend.repository;

import com.example.attendancebackend.entity.AttendanceRecord;
import com.example.attendancebackend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository
        extends JpaRepository<AttendanceRecord, Long> {

    Optional<AttendanceRecord> findByEmployeeAndWorkDate(
            Employee employee,
            LocalDate workDate
    );

    List<AttendanceRecord> findByEmployeeOrderByWorkDateDesc(
            Employee employee
    );
}