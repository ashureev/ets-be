package com.ets.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/employee/salary")
@CrossOrigin(origins = "*")
public class EmployeeSalaryManagementController {

    @Autowired
    private EmployeeSalaryManagementService service;

    @PostMapping("/save")
    public ResponseEntity<?> saveSalary(@RequestBody EmployeeSalaryManagement employeeSalaryManagement) {
        try {
            return ResponseEntity.ok(service.saveSalary(employeeSalaryManagement));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> getAllSalaryByEmployeeId(@PathVariable Long employeeId) {
        try {
            return ResponseEntity.ok(service.getAllSalaryByEmployeeId(employeeId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getSalaryById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getSalaryById(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSalary(@PathVariable Long id,
                                         @RequestBody EmployeeSalaryManagement employeeSalaryManagement) {
        try {
            return ResponseEntity.ok(service.updateSalary(id, employeeSalaryManagement));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSalary(@PathVariable Long id) {
        try {
            service.deleteSalary(id);
            return ResponseEntity.ok(Map.of("message", "Salary record deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> getFilteredSalary(
            @RequestParam Long employeeId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        try {
            return ResponseEntity.ok(service.getFilteredSalary(employeeId, year, month));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> getSalaryWithPagination(
            @RequestParam Long employeeId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            return ResponseEntity.ok(service.getSalaryWithPagination(employeeId, year, month, page, size));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getSalarySummary(
            @RequestParam Long employeeId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        try {
            return ResponseEntity.ok(service.getSalarySummary(employeeId, year, month));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardData(
            @RequestParam Long employeeId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            Map<String, BigDecimal> summary = service.getSalarySummary(employeeId, year, month);
            Page<EmployeeSalaryManagement> records = service.getSalaryWithPagination(employeeId, year, month, page, size);

            Map<String, Object> result = new java.util.HashMap<>();
            result.put("summary", summary);
            result.put("records", records != null ? records.getContent() : List.of());
            result.put("currentPage", records != null ? records.getNumber() : 0);
            result.put("totalPages", records != null ? records.getTotalPages() : 0);
            result.put("totalRecords", records != null ? records.getTotalElements() : 0);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("message", "Dashboard Error: " + e.getMessage()));
        }
    }
}
