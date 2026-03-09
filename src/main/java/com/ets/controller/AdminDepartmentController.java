package com.ets.controller;
	import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

	import com.ets.model.AdminDepartment;
import com.ets.model.Attendance;
import com.ets.service.AdminDepartmentService;

	@RestController
	@RequestMapping("/api/admin/departments")
	@CrossOrigin("*")
	public class AdminDepartmentController {

	    private final AdminDepartmentService service;

	    public AdminDepartmentController(AdminDepartmentService service) {
	        this.service = service;
	    }

	    // ➕ Add Department (Button: Add Department)
	    
	    @PostMapping("/create")
	    public ResponseEntity<String> addDepartment(
	            @RequestBody AdminDepartment request) {

	        service.addDepartment(request.getName());

	        return new ResponseEntity<>(
	                "Department created successfully",
	                HttpStatus.CREATED);
	    }


	    // 📋 Show All Departments (Cards)
	    @GetMapping("/fetch")
	    public ResponseEntity<List<AdminDepartment>> getAllDepartments() {

	        return ResponseEntity.ok(service.getAllDepartments());
	    }

	    
	    // ❌ Delete Department (Delete Button)
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {

	        service.deleteDepartment(id);
	        return ResponseEntity.ok("Department deleted successfully");
	    }
	}
	
	
	
	
	


