

package com.ets.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ets.model.AdminEmployee;
import com.ets.service.AdminEmployeeService;

@RestController
@RequestMapping("/admin/employees")
public class AdminEmployeeController {

    private final AdminEmployeeService service;

    public AdminEmployeeController(AdminEmployeeService service) {
        this.service = service;
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<?> getAllEmployees() {

        List<AdminEmployee> list = service.getAllEmployees();

        if (list.isEmpty()) {
            return ResponseEntity.ok(Map.of("message", "No employees found"));
        }

        return ResponseEntity.ok(list);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {

        AdminEmployee emp = service.getEmployee(id);

        if (emp == null) {
            return ResponseEntity.ok(Map.of("message", "Employee not found"));
        }

        return ResponseEntity.ok(emp);
    }

    // ADD
    @PostMapping
    public ResponseEntity<AdminEmployee> addEmployee(@RequestBody AdminEmployee employee) {
        return ResponseEntity.ok(service.addEmployee(employee));
    }

    // DEACTIVATE
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {

        AdminEmployee emp = service.getEmployee(id);

        if (emp == null) {
            return ResponseEntity.ok(Map.of("message", "Employee not found"));
        }

        service.deactivateEmployee(id);
        return ResponseEntity.ok(Map.of("message", "Employee " + id + " deactivated successfully"));
    }

    // ACTIVATE
    @PutMapping("/{id}/activate")
    public ResponseEntity<?> activate(@PathVariable Long id) {

        AdminEmployee emp = service.getEmployee(id);

        if (emp == null) {
            return ResponseEntity.ok(Map.of("message", "Employee not found"));
        }

        service.activateEmployee(id);
        return ResponseEntity.ok(Map.of("message", "Employee " + id + " activated successfully"));
    }

    // ACTIVE COUNT
    @GetMapping("/active/count")
    public ResponseEntity<?> activeEmployeesCount() {
        return ResponseEntity.ok(service.getActiveEmployeesCount());
    }
}