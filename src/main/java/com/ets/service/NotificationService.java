package com.ets.service;

import com.ets.model.Notification;
import com.ets.model.Employee;
import com.ets.repository.NotificationRepository;
import com.ets.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        return notificationRepository.findByEmployeeAndIsReadFalseOrderByTimestampDesc(employee);
    }

    public long getUnreadCount(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return notificationRepository.countByEmployeeAndIsReadFalse(employee);
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void createNotification(String email, String title, String message) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        
        Notification notification = Notification.builder()
                .employee(employee)
                .title(title)
                .message(message)
                .timestamp(LocalDateTime.now())
                .isRead(false)
                .build();
        
        notificationRepository.save(notification);
    }
}
