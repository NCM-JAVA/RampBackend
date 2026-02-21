package com.ramp.controller;

import com.ramp.dto.DashboardResponseDTO;
import com.ramp.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public DashboardResponseDTO getDashboard(
            @RequestParam String year,
            @RequestParam(required = false) String intervention,
            @RequestParam(required = false) String district
    ) {
        return dashboardService.getDashboard(year, intervention, district);
    }
}