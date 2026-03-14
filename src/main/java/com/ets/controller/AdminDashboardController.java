package com.ets.controller;

import com.ets.repository.AdminEmployeeRepository;
import com.ets.model.AdminEmployee;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/dashboard")
@CrossOrigin(origins = "*")
public class AdminDashboardController {

    private final AdminEmployeeRepository employeeRepository;

    public AdminDashboardController(AdminEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/summary")
    public Map<String, Object> getSummary() {
        Map<String, Object> summary = new HashMap<>();
        
        long totalEmployees = employeeRepository.count();
        long activeEmployees = employeeRepository.countByActiveTrue();
        
        // Mocking attendance since we don't have a direct 'today' count method in the repo usually
        // Typically we'd call attendanceRepository.countByDate(LocalDate.now())
        long presentToday = totalEmployees > 0 ? (long)(totalEmployees * 0.8) : 0; 
        
        long completedTasks = 124; // Mock or calculate from taskRepository
        long pendingTasks = 57;   // Mock or calculate from taskRepository

        summary.put("totalEmployees", totalEmployees);
        summary.put("activeEmployees", activeEmployees);
        summary.put("presentToday", presentToday);
        summary.put("completedTasks", completedTasks);
        summary.put("pendingTasks", pendingTasks);
        
        // Fetch 5 most recent employees
        List<AdminEmployee> recent = employeeRepository.findAll().stream()
                .sorted((a, b) -> b.getId().compareTo(a.getId()))
                .limit(5)
                .collect(Collectors.toList());
        
        summary.put("recentEmployees", recent);
        
        return summary;
    }
}
