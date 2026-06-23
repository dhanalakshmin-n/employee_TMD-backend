package com.employeetmd.dto;

import com.employeetmd.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String email;
    private Role role;
    private Long employeeId;
    private String name;
}
