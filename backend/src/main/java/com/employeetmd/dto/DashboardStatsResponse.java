package com.employeetmd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStatsResponse {

    private long totalEmployees;
    private long totalTasks;
    private long pendingTasks;
    private long completedTasks;
    private Map<String, Long> statusDistribution;
}
