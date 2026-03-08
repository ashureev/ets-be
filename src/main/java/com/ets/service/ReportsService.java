package com.ets.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ets.model.Reports;
import com.ets.repository.ReportsRepository;

@Service
public class ReportsService {

    private final ReportsRepository reportsRepository;

    public ReportsService(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    public Reports saveReport(Reports reports) {
        return reportsRepository.save(reports);
    }

    public List<Reports> getAllReports() {
        return reportsRepository.findAll();
    }

    public Reports getReportById(Long id) {
        return reportsRepository.findById(id).orElse(null);
    }
}