package com.employeetmd.service;

import com.employeetmd.dto.LoginRequest;
import com.employeetmd.dto.LoginResponse;
import com.employeetmd.entity.Employee;
import com.employeetmd.enums.Role;
import com.employeetmd.exception.UnauthorizedException;
import com.employeetmd.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String MANAGER_EMAIL = "manager@test.com";

    private final EmployeeRepository employeeRepository;

    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail().trim();

        if (MANAGER_EMAIL.equalsIgnoreCase(email)) {
            return LoginResponse.builder()
                    .email(MANAGER_EMAIL)
                    .role(Role.MANAGER)
                    .name("Manager")
                    .employeeId(null)
                    .build();
        }

        return employeeRepository.findByEmailIgnoreCase(email)
                .map(this::toEmployeeLoginResponse)
                .orElseThrow(() -> new UnauthorizedException(
                        "Invalid email. Use manager@test.com or a registered employee email."));
    }

    private LoginResponse toEmployeeLoginResponse(Employee employee) {
        return LoginResponse.builder()
                .email(employee.getEmail())
                .role(Role.EMPLOYEE)
                .employeeId(employee.getId())
                .name(employee.getName())
                .build();
    }
}
