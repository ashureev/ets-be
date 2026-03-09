package com.ets.controller;
import org.springframework.web.bind.annotation.*;

import com.ets.dto.EmployeeDashboardDTO;
import com.ets.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class EmpdashboardController {

    private final DashboardService service;

    public EmpdashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/{employeeId}")
    public EmployeeDashboardDTO dashboard(@PathVariable Long employeeId) {
        return service.getDashboard(employeeId);
    }
}