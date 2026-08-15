package com.example.attendancebackend.service;

import com.example.attendancebackend.dto.LoginRequest;
import com.example.attendancebackend.dto.LoginResponse;
import com.example.attendancebackend.entity.Employee;
import com.example.attendancebackend.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final EmployeeRepository employeeRepository;

    public AuthService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public LoginResponse login(LoginRequest request) {

        Employee employee = employeeRepository
                .findByEmployeeCode(request.getEmployeeCode())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "社員番号またはパスワードが正しくありません"
                        )
                );

        if (!employee.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "社員番号またはパスワードが正しくありません"
            );
        }

        return new LoginResponse(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getName()
        );
    }
}