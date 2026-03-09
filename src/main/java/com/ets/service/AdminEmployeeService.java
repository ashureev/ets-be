
package com.ets.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ets.model.AdminEmployee;
import com.ets.repository.AdminEmployeeRepository;

@Service
public class AdminEmployeeService {

    private final AdminEmployeeRepository repository;

    public AdminEmployeeService(AdminEmployeeRepository repository) {
        this.repository = repository;
    }

    public List<AdminEmployee> getAllEmployees() {
        return repository.findAll();
    }

    public AdminEmployee getEmployee(Long id) {
        return repository.findById(id).orElse(null);
    }

    public AdminEmployee addEmployee(AdminEmployee employee) {
        employee.setActive(true);
        return repository.save(employee);
    }

    public void deactivateEmployee(Long id) {
        AdminEmployee employee = getEmployee(id);
        if (employee != null) {
            employee.setActive(false);
            repository.save(employee);
        }
    }

    public void activateEmployee(Long id) {
        AdminEmployee employee = getEmployee(id);
        if (employee != null) {
            employee.setActive(true);
            repository.save(employee);
        }
    }

    public long getActiveEmployeesCount() {
        return repository.countByActiveTrue();
    }
}