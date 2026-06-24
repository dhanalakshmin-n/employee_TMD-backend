package com.employeetmd.dto;

import com.employeetmd.enums.Priority;
import com.employeetmd.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private Priority priority;
    private TaskStatus status;
    private LocalDate dueDate;
    private Long assignedEmployeeId;
    private String assignedEmployeeName;
    private LocalDateTime createdAt;
}
