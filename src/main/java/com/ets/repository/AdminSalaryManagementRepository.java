package com.ets.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ets.model.AdminSalaryManagement;

@Repository
public interface AdminSalaryManagementRepository extends JpaRepository<AdminSalaryManagement, Long> {

    List<AdminSalaryManagement> findByDepartment(String department);

    List<AdminSalaryManagement> findByEmployeeNameContainingIgnoreCase(String employeeName);
    @Query("SELECT a FROM AdminSalaryManagement a WHERE a.status = :status")

    List<AdminSalaryManagement> findByStatus(String status);

    Optional<AdminSalaryManagement> findByEmployeeCode(String employeeCode);
}