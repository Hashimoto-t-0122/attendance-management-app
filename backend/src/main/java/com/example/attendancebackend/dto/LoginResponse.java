package com.example.attendancebackend.dto;

public class LoginResponse {

    private final Long id;
    private final String employeeCode;
    private final String name;

    public LoginResponse(
            Long id,
            String employeeCode,
            String name
    ) {
        this.id = id;
        this.employeeCode = employeeCode;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public String getName() {
        return name;
    }
}