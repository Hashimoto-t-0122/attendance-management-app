package com.example.attendancebackend.controller;

import com.example.attendancebackend.dto.EmployeeResponse;
import com.example.attendancebackend.service.EmployeeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "https://attendance-management-app-pi.vercel.app"
})
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(
            EmployeeService employeeService
    ) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeResponse> findAllEmployees() {
        return employeeService.findAllEmployees();
    }
}