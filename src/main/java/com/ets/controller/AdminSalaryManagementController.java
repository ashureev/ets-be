package com.ets.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ets.model.AdminSalaryManagement;
import com.ets.service.AdminSalaryManagementService;

@RestController
@RequestMapping("/api/admin/salary-management")
@CrossOrigin(origins = "*")
public class AdminSalaryManagementController {

    private final AdminSalaryManagementService service;

    public AdminSalaryManagementController(AdminSalaryManagementService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AdminSalaryManagement> createSalary(@RequestBody AdminSalaryManagement salary) {
        return ResponseEntity.ok(service.saveSalary(salary));
    }

    @GetMapping
    public ResponseEntity<List<AdminSalaryManagement>> getAllSalaries() {
        return ResponseEntity.ok(service.getAllSalaries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminSalaryManagement> getSalaryById(@PathVariable Long id) {
        return service.getSalaryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<AdminSalaryManagement>> getByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(service.getByDepartment(department));
    }

    @GetMapping("/search")
    public ResponseEntity<List<AdminSalaryManagement>> searchByEmployeeName(@RequestParam String name) {
        return ResponseEntity.ok(service.searchByEmployeeName(name));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AdminSalaryManagement>> getByStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminSalaryManagement> updateSalary(@PathVariable Long id,
                                                              @RequestBody AdminSalaryManagement salary) {
        return ResponseEntity.ok(service.updateSalary(id, salary));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSalary(@PathVariable Long id) {
        service.deleteSalary(id);
        return ResponseEntity.ok("Salary record deleted successfully");
    }
}