package com.ets.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ets.model.AdminDepartment;
import com.ets.repository.AdminDepartmentRepository;

@Service
public class AdminDepartmentService {

    private final AdminDepartmentRepository repository;

    public AdminDepartmentService(AdminDepartmentRepository repository) {
        this.repository = repository;
    }

 
 // ✅ Add Department
    public AdminDepartment addDepartment(String name) {

        if (repository.existsByName(name)) {
            throw new RuntimeException("Department already exists");
        }

        AdminDepartment department = new AdminDepartment();
        department.setName(name);

        return repository.save(department);
    }

    // ✅ Get All Departments
    public List<AdminDepartment> getAllDepartments() {
        return repository.findAll();
    }

 // ✅ Delete Department
    public void deleteDepartment(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Department not found with id: " + id);
        }

        repository.deleteById(id);
    }
}