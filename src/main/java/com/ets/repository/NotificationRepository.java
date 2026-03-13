package com.ets.repository;

import com.ets.model.Notification;
import com.ets.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByEmployeeOrderByTimestampDesc(Employee employee);
    List<Notification> findByEmployeeAndReadFalseOrderByTimestampDesc(Employee employee);
    long countByEmployeeAndReadFalse(Employee employee);
}
