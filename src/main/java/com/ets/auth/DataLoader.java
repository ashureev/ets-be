package com.ets.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ets.enums.ChallengeStatus;
import com.ets.enums.DifficultyLevel;

import com.ets.model.CodingChallenge;
import com.ets.model.EmployeeSalaryManagement;
import com.ets.model.Notification;
import com.ets.model.AddUsers;
import com.ets.model.AdminLoginUser;
import com.ets.model.AdminAttendanceRecords;
import com.ets.model.Employee;
import com.ets.repository.CodingChallengeRepository;
import com.ets.repository.EmployeeSalaryManagementRepository;
import com.ets.repository.NotificationRepository;
import com.ets.repository.EmployeeRepository;
import com.ets.repository.AdminLoginUserRepository;
import com.ets.repository.AdminAttendanceRecordRepository;
import com.ets.repository.AddUsersRepository;
import com.ets.enums.AttendenceStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(CodingChallengeRepository repository,
            EmployeeSalaryManagementRepository salaryRepository,
            NotificationRepository notificationRepository,
            EmployeeRepository employeeRepository,
            AdminLoginUserRepository adminRepository,
            AdminAttendanceRecordRepository attRepository,
            AddUsersRepository addUsersRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            if (repository.count() == 0) {

                CodingChallenge twoSum = new CodingChallenge();
                twoSum.setTitle("Two Sum");
                twoSum.setDescription("Write a function to solve the Two Sum problem efficiently.");
                twoSum.setDifficulty(DifficultyLevel.EASY);
                twoSum.setStatus(ChallengeStatus.SOLVED);
                twoSum.setSolveUrl("https://leetcode.com/problems/two-sum/");

                CodingChallenge reverseLinkedList = new CodingChallenge();
                reverseLinkedList.setTitle("Reverse Linked List");
                reverseLinkedList
                        .setDescription("Write a function to solve the Reverse Linked List problem efficiently.");
                reverseLinkedList.setDifficulty(DifficultyLevel.MEDIUM);
                reverseLinkedList.setStatus(ChallengeStatus.PENDING);
                reverseLinkedList.setSolveUrl("https://leetcode.com/problems/reverse-linked-list/");

                CodingChallenge validPalindrome = new CodingChallenge();
                validPalindrome.setTitle("Valid Palindrome");
                validPalindrome.setDescription("Write a function to solve the Valid Palindrome problem efficiently.");
                validPalindrome.setDifficulty(DifficultyLevel.EASY);
                validPalindrome.setStatus(ChallengeStatus.PENDING);
                validPalindrome.setSolveUrl("https://leetcode.com/problems/valid-palindrome/");

                repository.save(twoSum);
                repository.save(reverseLinkedList);
                repository.save(validPalindrome);
            }

            if (salaryRepository.count() == 0) {
                EmployeeSalaryManagement salary1 = new EmployeeSalaryManagement();
                salary1.setEmployeeId(1L);
                salary1.setCycle("Monthly");
                salary1.setTransferDate(LocalDate.now().minusMonths(1));
                salary1.setGross(new BigDecimal("50000"));
                salary1.setDeductions(new BigDecimal("5000"));
                salary1.setNetAmount(new BigDecimal("45000"));
                salary1.setTransactionStatus("Completed");

                EmployeeSalaryManagement salary2 = new EmployeeSalaryManagement();
                salary2.setEmployeeId(1L);
                salary2.setCycle("Monthly");
                salary2.setTransferDate(LocalDate.now());
                salary2.setGross(new BigDecimal("52000"));
                salary2.setDeductions(new BigDecimal("5200"));
                salary2.setNetAmount(new BigDecimal("46800"));
                salary2.setTransactionStatus("Pending");

                salaryRepository.save(salary1);
                salaryRepository.save(salary2);
            }

            if (notificationRepository.count() == 0) {
                // We need an employee to attach notifications to.
                // Since our testuser@gmail.com is created via controller login,
                // it might not exist during app startup until first login.
                // Let's seed a base employee for testing UI if they're missing.
                String testEmail = "testuser@gmail.com";
                com.ets.model.Employee employee = employeeRepository.findByEmail(testEmail).orElse(null);

                if (employee == null) {
                    employee = new com.ets.model.Employee();
                    employee.setEmail(testEmail);
                    employee.setUsername("testuser");
                    employee.setRole(com.ets.enums.Role.EMPLOYEE);
                    employee.setPassword("testpwd");
                    employeeRepository.save(employee);
                }

                Notification n1 = Notification.builder()
                        .employee(employee)
                        .title("Welcome to ETS!")
                        .message("We're glad to have you here. Explore your dashboard to get started.")
                        .timestamp(LocalDateTime.now().minusHours(2))
                        .read(false)
                        .build();

                Notification n2 = Notification.builder()
                        .employee(employee)
                        .title("New Coding Challenge")
                        .message("A new challenge 'Two Sum' has been assigned to you. Go check it out!")
                        .timestamp(LocalDateTime.now().minusMinutes(45))
                        .read(false)
                        .build();

                notificationRepository.save(n1);
                notificationRepository.save(n2);
            }

            if (adminRepository.count() == 0) {
                AdminLoginUser admin = new AdminLoginUser();
                admin.setUsername("admin");
                admin.setEmail("admin@ets.com");
                admin.setPasswordHash(passwordEncoder.encode("admin123"));
                admin.setRole(com.ets.enums.Role.ADMIN);
                admin.setEnabled(true);
                adminRepository.save(admin);
            }

            if (attRepository.count() == 0) {
                AdminAttendanceRecords rec = new AdminAttendanceRecords();
                rec.setName("testuser");
                rec.setDate(LocalDate.now());
                rec.setStatus(AttendenceStatus.PRESENT);
                rec.setLoginTime(LocalTime.of(9, 0));
                rec.setLogoutTime(LocalTime.of(18, 0));
                attRepository.save(rec);
            }

            if (addUsersRepository.count() == 0) {
                AddUsers suneetha = new AddUsers();
                suneetha.setNameUsername("Suneetha");
                suneetha.setEmailAddress("suneetha@aja.com");
                suneetha.setAccessPassword("SUNI_PWD_2026");
                suneetha.setPhone("+91 9666477844");
                suneetha.setDept("Engineering");
                addUsersRepository.save(suneetha);

                // Also add other mock names for consistency with the frontend image
                String[] names = { "Sravani", "Arjun", "Chandrasekar", "Siva" };
                String[] emails = { "sravani@aja.com", "arjun@aja.com", "chandrasekar@aja.com", "siva@aja.com" };
                for (int i = 0; i < names.length; i++) {
                    AddUsers user = new AddUsers();
                    user.setNameUsername(names[i]);
                    user.setEmailAddress(emails[i]);
                    user.setAccessPassword("AJA_USER_" + i);
                    user.setPhone("+91 000000000" + i);
                    user.setDept("General");
                    addUsersRepository.save(user);
                }
            }

            // Ensure Suneetha is also in the Employee table for login/roles if needed
            if (employeeRepository.findByEmail("suneetha@aja.com").isEmpty()) {
                Employee suneethaEmp = new Employee();
                suneethaEmp.setUsername("Suneetha");
                suneethaEmp.setEmail("suneetha@aja.com");
                suneethaEmp.setPassword(passwordEncoder.encode("SUNI_PWD_2026"));
                suneethaEmp.setRole(com.ets.enums.Role.EMPLOYEE);
                employeeRepository.save(suneethaEmp);
            }
        };
    }
}