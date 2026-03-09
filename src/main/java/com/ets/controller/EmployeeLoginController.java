package com.ets.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ets.model.EmployeeLogin;
import com.ets.service.EmployeeLoginService;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "*")
public class EmployeeLoginController {

    private final EmployeeLoginService employeeLoginService;

    public EmployeeLoginController(EmployeeLoginService employeeLoginService) {
        this.employeeLoginService = employeeLoginService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody EmployeeLogin employeeLogin) {
        String response = employeeLoginService.register(employeeLogin);

        if (response.equals("Employee already exists with this email")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody EmployeeLogin employeeLogin) {
        String response = employeeLoginService.login(employeeLogin);

        if (response.equals("Invalid email or password")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String emailAddress = request.get("emailAddress");
        String response = employeeLoginService.forgotPassword(emailAddress);

        if (response.equals("Email not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String emailAddress = request.get("emailAddress");
        String newPassword = request.get("newPassword");
        String confirmPassword = request.get("confirmPassword");

        String response = employeeLoginService.resetPassword(emailAddress, newPassword, confirmPassword);

        if (response.equals("Email not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (response.equals("New password and confirm password do not match")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
