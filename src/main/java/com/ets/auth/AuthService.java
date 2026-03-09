package com.ets.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ets.model.Employee;
import com.ets.repository.EmployeeRepository;

@Service
public class AuthService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    // REGISTER (Employee)
    public Employee register(Employee employee) {
        // Encrypt password before saving
        employee.setPassword(encoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    // LOGIN (Employee -> returns JWT token)
    public String login(String email, String password) {

        Employee employee = employeeRepository.findByEmail(email.trim())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Check password
        if (!encoder.matches(password, employee.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate JWT token
        return jwtService.createToken(employee.getEmail(), "EMPLOYEE");
    }
}