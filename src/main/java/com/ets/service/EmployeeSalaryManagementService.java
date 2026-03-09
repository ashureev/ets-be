package com.ets.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ets.model.EmployeeSalaryManagement;
import com.ets.repository.EmployeeSalaryManagementRepository;

@Service
public class EmployeeSalaryManagementService {

    @Autowired
    private EmployeeSalaryManagementRepository repository;

    public EmployeeSalaryManagement saveSalary(EmployeeSalaryManagement employeeSalaryManagement) {
        if (employeeSalaryManagement.getNetAmount() == null &&
                employeeSalaryManagement.getGross() != null &&
                employeeSalaryManagement.getDeductions() != null) {
            employeeSalaryManagement.setNetAmount(
                    employeeSalaryManagement.getGross().subtract(employeeSalaryManagement.getDeductions())
            );
        }
        return repository.save(employeeSalaryManagement);
    }

    public List<EmployeeSalaryManagement> getAllSalaryByEmployeeId(Long employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public EmployeeSalaryManagement getSalaryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary record not found with id: " + id));
    }

    public EmployeeSalaryManagement updateSalary(Long id, EmployeeSalaryManagement employeeSalaryManagement) {
        EmployeeSalaryManagement existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary record not found with id: " + id));

        existing.setEmployeeId(employeeSalaryManagement.getEmployeeId());
        existing.setCycle(employeeSalaryManagement.getCycle());
        existing.setTransferDate(employeeSalaryManagement.getTransferDate());
        existing.setGross(employeeSalaryManagement.getGross());
        existing.setDeductions(employeeSalaryManagement.getDeductions());

        if (employeeSalaryManagement.getGross() != null && employeeSalaryManagement.getDeductions() != null) {
            existing.setNetAmount(
                    employeeSalaryManagement.getGross().subtract(employeeSalaryManagement.getDeductions())
            );
        } else {
            existing.setNetAmount(employeeSalaryManagement.getNetAmount());
        }

        existing.setTransactionStatus(employeeSalaryManagement.getTransactionStatus());
        existing.setViewUrl(employeeSalaryManagement.getViewUrl());
        existing.setDownloadUrl(employeeSalaryManagement.getDownloadUrl());

        return repository.save(existing);
    }

    public void deleteSalary(Long id) {
        EmployeeSalaryManagement existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary record not found with id: " + id));
        repository.delete(existing);
    }

    public List<EmployeeSalaryManagement> getFilteredSalary(Long employeeId, Integer year, Integer month) {
        if (year != null && month != null) {
            return repository.findByEmployeeIdAndYearAndMonth(employeeId, year, month);
        } else if (year != null) {
            return repository.findByEmployeeIdAndYear(employeeId, year);
        } else {
            return repository.findByEmployeeId(employeeId);
        }
    }

    public Page<EmployeeSalaryManagement> getSalaryWithPagination(Long employeeId, Integer year, Integer month, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        if (year != null && month != null) {
            return repository.findByEmployeeIdAndYearAndMonth(employeeId, year, month, pageable);
        } else if (year != null) {
            return repository.findByEmployeeIdAndYear(employeeId, year, pageable);
        } else {
            return repository.findByEmployeeId(employeeId, pageable);
        }
    }

    public Map<String, BigDecimal> getSalarySummary(Long employeeId, Integer year, Integer month) {
        List<EmployeeSalaryManagement> allRecords = repository.findByEmployeeId(employeeId);
        List<EmployeeSalaryManagement> filteredRecords = getFilteredSalary(employeeId, year, month);

        BigDecimal totalNetValue = allRecords.stream()
                .map(EmployeeSalaryManagement::getNetAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal filteredPeriodGross = filteredRecords.stream()
                .map(EmployeeSalaryManagement::getGross)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal filteredPeriodDeductions = filteredRecords.stream()
                .map(EmployeeSalaryManagement::getDeductions)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> summary = new HashMap<>();
        summary.put("totalNetValue", totalNetValue);
        summary.put("filteredPeriodGross", filteredPeriodGross);
        summary.put("filteredPeriodDeductions", filteredPeriodDeductions);

        return summary;
    }
}