package com.ets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ets.model.Employee;
import org.springframework.security.crypto.password.PasswordEncoder;   

import com.ets.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public Employee createEmployee(Employee emp) {
        emp.setPassword(encoder.encode(emp.getPassword()));
        return repository.save(emp);   
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public void deleteEmployee(Long empId) {
        repository.deleteById(empId);
    }

	
}
