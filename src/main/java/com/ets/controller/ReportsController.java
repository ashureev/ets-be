package com.ets.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ets.model.Reports;
import com.ets.service.ReportsService;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    private final ReportsService reportsService;

    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @PostMapping
    public Reports createReport(@RequestBody Reports reports) {
        return reportsService.saveReport(reports);
    }

    @GetMapping
    public List<Reports> getAllReports() {
        return reportsService.getAllReports();
    }

    @GetMapping("/{id}")
    public Reports getReport(@PathVariable Long id) {
        return reportsService.getReportById(id);
    }
}