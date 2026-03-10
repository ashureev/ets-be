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
import com.ets.repository.EmployeeRepository;
import com.ets.auth.JwtService;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "*")
public class EmployeeLoginController {

    private final EmployeeLoginService employeeLoginService;
    private final JwtService jwtService;
    private final EmployeeRepository employeeRepository;

    public EmployeeLoginController(EmployeeLoginService employeeLoginService, JwtService jwtService, EmployeeRepository employeeRepository) {
        this.employeeLoginService = employeeLoginService;
        this.jwtService = jwtService;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> request) {
        try {
            String email = request.containsKey("emailAddress") ? request.get("emailAddress") : request.get("email");
            String password = request.get("password");
            String role = request.get("role");

            if (email == null || email.isBlank() || password == null || password.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email or password missing in request");
            }

            EmployeeLogin employeeLogin = new EmployeeLogin();
            employeeLogin.setEmailAddress(email);
            employeeLogin.setPassword(password);
            employeeLogin.setRole(role);

            String response = employeeLoginService.register(employeeLogin);

            if ("Employee already exists with this email".equals(response)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.containsKey("emailAddress") ? request.get("emailAddress") : request.get("email");
            String password = request.get("password");

            if (email == null || email.isBlank() || password == null || password.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Email or password missing in request"));
            }

            EmployeeLogin employeeLogin = new EmployeeLogin();
            employeeLogin.setEmailAddress(email);
            employeeLogin.setPassword(password);

            EmployeeLogin loggedInEmployee = employeeLoginService.login(employeeLogin);

            if (loggedInEmployee == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid email or password"));
            }

            // Self-heal: Ensure the user exists in `emp_details` for Attendance/Tasks if they were only registered in employee_login natively
            if (employeeRepository.findByEmail(loggedInEmployee.getEmailAddress()).isEmpty()) {
                com.ets.model.Employee emp = new com.ets.model.Employee();
                emp.setEmail(loggedInEmployee.getEmailAddress());
                emp.setPassword(loggedInEmployee.getPassword());
                emp.setRole(com.ets.enums.Role.EMPLOYEE);
                emp.setUsername(loggedInEmployee.getEmailAddress().split("@")[0]);
                employeeRepository.save(emp);
            }

            String role = loggedInEmployee.getRole() != null ? loggedInEmployee.getRole() : "EMPLOYEE";
            String token = jwtService.createToken(loggedInEmployee.getEmailAddress(), role);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "user", loggedInEmployee.getEmailAddress(),
                    "role", role
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Internal error: " + e.getMessage()));
        }
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
