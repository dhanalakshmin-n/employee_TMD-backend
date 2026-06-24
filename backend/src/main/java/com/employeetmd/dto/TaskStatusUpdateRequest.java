package com.employeetmd.dto;

import com.employeetmd.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusUpdateRequest {

    @NotNull(message = "Status is required")
    private TaskStatus status;
}
