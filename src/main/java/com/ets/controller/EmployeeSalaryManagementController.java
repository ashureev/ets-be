package com.ets.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ets.model.EmployeeSalaryManagement;
import com.ets.service.EmployeeSalaryManagementService;

@RestController
@RequestMapping("/employee-salary-management")
@CrossOrigin(origins = "*")
public class EmployeeSalaryManagementController {

    @Autowired
    private EmployeeSalaryManagementService service;

    @PostMapping("/save")
    public EmployeeSalaryManagement saveSalary(@RequestBody EmployeeSalaryManagement employeeSalaryManagement) {
        return service.saveSalary(employeeSalaryManagement);
    }

    @GetMapping("/employee/{employeeId}")
    public List<EmployeeSalaryManagement> getAllSalaryByEmployeeId(@PathVariable Long employeeId) {
        return service.getAllSalaryByEmployeeId(employeeId);
    }

    @GetMapping("/id/{id}")
    public EmployeeSalaryManagement getSalaryById(@PathVariable Long id) {
        return service.getSalaryById(id);
    }

    @PutMapping("/update/{id}")
    public EmployeeSalaryManagement updateSalary(@PathVariable Long id,
                                                 @RequestBody EmployeeSalaryManagement employeeSalaryManagement) {
        return service.updateSalary(id, employeeSalaryManagement);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSalary(@PathVariable Long id) {
        service.deleteSalary(id);
        return "Salary record deleted successfully";
    }

    @GetMapping("/filter")
    public List<EmployeeSalaryManagement> getFilteredSalary(
            @RequestParam Long employeeId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        return service.getFilteredSalary(employeeId, year, month);
    }

    @GetMapping("/pagination")
    public Page<EmployeeSalaryManagement> getSalaryWithPagination(
            @RequestParam Long employeeId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return service.getSalaryWithPagination(employeeId, year, month, page, size);
    }

    @GetMapping("/summary")
    public Map<String, BigDecimal> getSalarySummary(
            @RequestParam Long employeeId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        return service.getSalarySummary(employeeId, year, month);
    }

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboardData(
            @RequestParam Long employeeId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Map<String, BigDecimal> summary = service.getSalarySummary(employeeId, year, month);
        Page<EmployeeSalaryManagement> records = service.getSalaryWithPagination(employeeId, year, month, page, size);

        return Map.of(
                "summary", summary,
                "records", records.getContent(),
                "currentPage", records.getNumber(),
                "totalPages", records.getTotalPages(),
                "totalRecords", records.getTotalElements()
        );
    }
}
