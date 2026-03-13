package com.ets.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ets.model.AddUsers;
import com.ets.repository.AddUsersRepository;

@Service
public class AddUsersService {

    private final AddUsersRepository addUsersRepository;
    private final com.ets.repository.EmployeeLoginRepository employeeLoginRepository;
    private final com.ets.repository.EmployeeRepository employeeRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public AddUsersService(AddUsersRepository addUsersRepository,
                          com.ets.repository.EmployeeLoginRepository employeeLoginRepository,
                          com.ets.repository.EmployeeRepository employeeRepository,
                          org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.addUsersRepository = addUsersRepository;
        this.employeeLoginRepository = employeeLoginRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AddUsers saveUser(AddUsers addUsers) {
        if (addUsersRepository.existsByEmailAddress(addUsers.getEmailAddress())) {
            throw new RuntimeException("Email already exists: " + addUsers.getEmailAddress());
        }
        
        // Save to AddUsers table
        AddUsers savedUser = addUsersRepository.save(addUsers);
        
        // Sync with EmployeeLogin table for Authentication
        if (!employeeLoginRepository.existsByEmailAddress(addUsers.getEmailAddress())) {
            com.ets.model.EmployeeLogin login = new com.ets.model.EmployeeLogin();
            login.setEmailAddress(addUsers.getEmailAddress());
            login.setPassword(passwordEncoder.encode(addUsers.getAccessPassword()));
            login.setRole("EMPLOYEE");
            employeeLoginRepository.save(login);
        }
        
        // Sync with Employee (emp_details) table for Attendance/Tasks
        if (employeeRepository.findByEmail(addUsers.getEmailAddress()).isEmpty()) {
            com.ets.model.Employee emp = new com.ets.model.Employee();
            emp.setEmail(addUsers.getEmailAddress());
            emp.setUsername(addUsers.getNameUsername());
            emp.setPassword(passwordEncoder.encode(addUsers.getAccessPassword()));
            emp.setRole(com.ets.enums.Role.EMPLOYEE);
            employeeRepository.save(emp);
        }
        
        return savedUser;
    }

    public List<AddUsers> getAllUsers() {
        return addUsersRepository.findAll();
    }

    public Optional<AddUsers> getUserById(Long id) {
        return addUsersRepository.findById(id);
    }

    public AddUsers updateUser(Long id, AddUsers addUsers) {
        AddUsers existingUser = addUsersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        String oldEmail = existingUser.getEmailAddress();
        
        Optional<AddUsers> userWithSameEmail =
                addUsersRepository.findByEmailAddress(addUsers.getEmailAddress());

        if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(id)) {
            throw new RuntimeException("Email already exists: " + addUsers.getEmailAddress());
        }

        existingUser.setNameUsername(addUsers.getNameUsername());
        existingUser.setEmailAddress(addUsers.getEmailAddress());
        existingUser.setAccessPassword(addUsers.getAccessPassword());
        existingUser.setPhone(addUsers.getPhone());
        existingUser.setDept(addUsers.getDept());

        AddUsers updatedUser = addUsersRepository.save(existingUser);
        
        // Sync update with EmployeeLogin
        employeeLoginRepository.findByEmailAddress(oldEmail).ifPresent(login -> {
            login.setEmailAddress(addUsers.getEmailAddress());
            if (addUsers.getAccessPassword() != null && !addUsers.getAccessPassword().isEmpty()) {
                login.setPassword(passwordEncoder.encode(addUsers.getAccessPassword()));
            }
            employeeLoginRepository.save(login);
        });
        
        // Sync update with Employee
        employeeRepository.findByEmail(oldEmail).ifPresent(emp -> {
            emp.setEmail(addUsers.getEmailAddress());
            emp.setUsername(addUsers.getNameUsername());
            if (addUsers.getAccessPassword() != null && !addUsers.getAccessPassword().isEmpty()) {
                emp.setPassword(passwordEncoder.encode(addUsers.getAccessPassword()));
            }
            employeeRepository.save(emp);
        });

        return updatedUser;
    }

    public void deleteUser(Long id) {
        AddUsers existingUser = addUsersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        String email = existingUser.getEmailAddress();
        addUsersRepository.delete(existingUser);
        
        // Sync delete
        employeeLoginRepository.findByEmailAddress(email).ifPresent(employeeLoginRepository::delete);
        employeeRepository.findByEmail(email).ifPresent(employeeRepository::delete);
    }
}