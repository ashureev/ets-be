package com.ets.service;

import com.ets.model.Attendance;
import com.ets.model.Employee;
import com.ets.repository.AttendanceRepository;
import com.ets.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    // ================= EMPLOYEE METHODS =================

    public Employee register(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {

        Employee employee = getEmployeeById(id);

        employee.setUsername(updatedEmployee.getUsername());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setPassword(updatedEmployee.getPassword());
        employee.setRole(updatedEmployee.getRole());

        return employeeRepository.save(employee);
    }

    public String deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        return "Employee deleted successfully";
    }

    // ================= ATTENDANCE METHODS =================

    public String checkIn(String email) {

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("CheckIn Error: Employee not found for email " + email));

        if (attendanceRepository
                .findByEmployeeAndDate(employee, LocalDate.now())
                .isPresent()) {
            return "Already checked in today";
        }

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .date(LocalDate.now())
                .loginTime(LocalDateTime.now())
                .status("Present")
                .workingHours("0h 0m")
                .build();

        attendanceRepository.save(attendance);

        return "Checked in successfully";
    }

    public String checkOut(String email) {

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("CheckOut Error: Employee not found for email " + email));

        Attendance attendance = attendanceRepository
                .findByEmployeeAndDate(employee, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("CheckOut Error: No check-in found today."));

        attendance.setLogoutTime(LocalDateTime.now());

        long minutes = Duration.between(
                attendance.getLoginTime(),
                attendance.getLogoutTime()
        ).toMinutes();

        attendance.setWorkingHours((minutes / 60) + "h " + (minutes % 60) + "m");

        if (minutes >= 480) {
            attendance.setStatus("Present");
        } else {
            attendance.setStatus("Half Day");
        }

        attendanceRepository.save(attendance);

        return "Checked out successfully";
    }

    public List<Attendance> weeklyHistory(String email) {

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return attendanceRepository
                .findTop7ByEmployeeOrderByDateDesc(employee);
    }
    
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

}
