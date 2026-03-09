package com.ets.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ets.model.EmployeeLogin;
import com.ets.repository.EmployeeLoginRepository;

@Service
public class EmployeeLoginService {

	private final EmployeeLoginRepository employeeLoginRepository;
	private final PasswordEncoder passwordEncoder;

	public EmployeeLoginService(EmployeeLoginRepository employeeLoginRepository, PasswordEncoder passwordEncoder) {
		this.employeeLoginRepository = employeeLoginRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public String register(EmployeeLogin employeeLogin) {
		if (employeeLoginRepository.existsByEmailAddress(employeeLogin.getEmailAddress())) {
			return "Employee already exists with this email";
		}

		employeeLogin.setPassword(passwordEncoder.encode(employeeLogin.getPassword()));

		if (employeeLogin.getRole() == null || employeeLogin.getRole().isBlank()) {
			employeeLogin.setRole("EMPLOYEE");
		} else {
			employeeLogin.setRole(employeeLogin.getRole().toUpperCase());
		}

		employeeLoginRepository.save(employeeLogin);
		return "Employee registered successfully";
	}

	public String login(EmployeeLogin employeeLogin) {
		EmployeeLogin existingEmployee = employeeLoginRepository.findByEmailAddress(employeeLogin.getEmailAddress())
				.orElse(null);

		if (existingEmployee == null) {
			return "Invalid email or password";
		}

		boolean passwordMatches = passwordEncoder.matches(employeeLogin.getPassword(), existingEmployee.getPassword());

		if (!passwordMatches) {
			return "Invalid email or password";
		}

		return "Login successful";
	}

	public String forgotPassword(String emailAddress) {
		EmployeeLogin existingEmployee = employeeLoginRepository.findByEmailAddress(emailAddress).orElse(null);

		if (existingEmployee == null) {
			return "Email not found";
		}

		return "Email verified. You can reset your password now";
	}

	public String resetPassword(String emailAddress, String newPassword, String confirmPassword) {
		EmployeeLogin existingEmployee = employeeLoginRepository.findByEmailAddress(emailAddress).orElse(null);

		if (existingEmployee == null) {
			return "Email not found";
		}

		if (!newPassword.equals(confirmPassword)) {
			return "New password and confirm password do not match";
		}

		existingEmployee.setPassword(passwordEncoder.encode(newPassword));
		employeeLoginRepository.save(existingEmployee);

		return "Password reset successfully";
	}
}
