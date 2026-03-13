package com.ets.service;

import com.ets.model.Notification;
import com.ets.model.Employee;
import com.ets.repository.NotificationRepository;
import com.ets.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmployeeRepository employeeRepository;

    public List<Notification> getNotificationsForEmployee(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return notificationRepository.findByEmployeeOrderByTimestampDesc(employee);
    }

    public List<Notification> getUnreadNotificationsForEmployee(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return notificationRepository.findByEmployeeAndReadFalseOrderByTimestampDesc(employee);
    }

    public long getUnreadCount(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return notificationRepository.countByEmployeeAndReadFalse(employee);
    }

    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
