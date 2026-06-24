package com.employeetmd.service;

import com.employeetmd.dto.DashboardStatsResponse;
import com.employeetmd.enums.TaskStatus;
import com.employeetmd.repository.EmployeeRepository;
import com.employeetmd.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public DashboardStatsResponse getStats() {
        long pending = taskRepository.countByStatus(TaskStatus.PENDING);
        long inProgress = taskRepository.countByStatus(TaskStatus.IN_PROGRESS);
        long completed = taskRepository.countByStatus(TaskStatus.COMPLETED);

        return DashboardStatsResponse.builder()
                .totalEmployees(employeeRepository.count())
                .totalTasks(taskRepository.count())
                .pendingTasks(pending)
                .completedTasks(completed)
                .statusDistribution(Map.of(
                        "PENDING", pending,
                        "IN_PROGRESS", inProgress,
                        "COMPLETED", completed
                ))
                .build();
    }
}
