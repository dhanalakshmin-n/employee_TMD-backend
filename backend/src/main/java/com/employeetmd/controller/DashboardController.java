package com.employeetmd.controller;

import com.employeetmd.dto.DashboardStatsResponse;
import com.employeetmd.dto.GenericResponse;
import com.employeetmd.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public ResponseEntity<GenericResponse<DashboardStatsResponse>> getStats() {
        DashboardStatsResponse stats = dashboardService.getStats();
        return ResponseEntity.ok(GenericResponse.success("Dashboard stats fetched successfully", stats));
    }
}
