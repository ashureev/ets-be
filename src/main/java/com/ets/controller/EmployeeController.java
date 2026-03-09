package com.ets.controller;

import com.ets.model.Employee;
import com.ets.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {


    @Autowired
    private EmployeeService service;

    // CREATE EMPLOYEE
    @PostMapping("/create")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee emp) {
        Employee savedEmployee = service.createEmployee(emp);
        return ResponseEntity.ok(savedEmployee);
    }

    // GET ALL EMPLOYEES
    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = service.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // DELETE EMPLOYEE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
}
