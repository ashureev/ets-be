package com.ets.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ets.model.AdminSalaryManagement;
import com.ets.repository.AdminSalaryManagementRepository;

@Service
public class AdminSalaryManagementService {

    private final AdminSalaryManagementRepository repository;

    public AdminSalaryManagementService(AdminSalaryManagementRepository repository) {
        this.repository = repository;
    }

    public AdminSalaryManagement saveSalary(AdminSalaryManagement salary) {
        repository.findByEmployeeCode(salary.getEmployeeCode())
                .ifPresent(existing -> {
                    throw new RuntimeException("Employee code already exists: " + salary.getEmployeeCode());
                });

        return repository.save(salary);
    }

    public List<AdminSalaryManagement> getAllSalaries() {
        return repository.findAll();
    }

    public Optional<AdminSalaryManagement> getSalaryById(Long id) {
        return repository.findById(id);
    }

    public List<AdminSalaryManagement> getByDepartment(String department) {
        return repository.findByDepartment(department);
    }

    public List<AdminSalaryManagement> searchByEmployeeName(String employeeName) {
        return repository.findByEmployeeNameContainingIgnoreCase(employeeName);
    }

    public List<AdminSalaryManagement> getByStatus(String status) {
        return repository.findByStatus(status);
    }

    public AdminSalaryManagement updateSalary(Long id, AdminSalaryManagement salary) {
        AdminSalaryManagement existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary record not found with id: " + id));

        if (salary.getEmployeeCode() != null &&
                !salary.getEmployeeCode().equals(existing.getEmployeeCode())) {

            repository.findByEmployeeCode(salary.getEmployeeCode())
                    .ifPresent(record -> {
                        throw new RuntimeException("Employee code already exists: " + salary.getEmployeeCode());
                    });
        }

        existing.setEmployeeCode(salary.getEmployeeCode());
        existing.setEmployeeName(salary.getEmployeeName());
        existing.setDesignation(salary.getDesignation());
        existing.setDepartment(salary.getDepartment());
        existing.setGrossSalary(salary.getGrossSalary());
        existing.setDeductions(salary.getDeductions());
        existing.setStatus(salary.getStatus());
        existing.setReceiptIssued(salary.getReceiptIssued());
        existing.setReceiptDate(salary.getReceiptDate());

        return repository.save(existing);
    }

    public void deleteSalary(Long id) {
        AdminSalaryManagement existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary record not found with id: " + id));

        repository.delete(existing);
    }
}