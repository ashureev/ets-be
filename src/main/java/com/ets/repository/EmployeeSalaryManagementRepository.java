package com.ets.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ets.model.EmployeeSalaryManagement;

public interface EmployeeSalaryManagementRepository extends JpaRepository<EmployeeSalaryManagement, Long> {

    List<EmployeeSalaryManagement> findByEmployeeId(Long employeeId);

    Page<EmployeeSalaryManagement> findByEmployeeId(Long employeeId, Pageable pageable);

    @Query("SELECT e FROM EmployeeSalaryManagement e WHERE e.employeeId = :employeeId AND YEAR(e.transferDate) = :year")
    List<EmployeeSalaryManagement> findByEmployeeIdAndYear(@Param("employeeId") Long employeeId,
                                                           @Param("year") Integer year);

    @Query("SELECT e FROM EmployeeSalaryManagement e WHERE e.employeeId = :employeeId AND YEAR(e.transferDate) = :year")
    Page<EmployeeSalaryManagement> findByEmployeeIdAndYear(@Param("employeeId") Long employeeId,
                                                           @Param("year") Integer year,
                                                           Pageable pageable);

    @Query("SELECT e FROM EmployeeSalaryManagement e WHERE e.employeeId = :employeeId AND YEAR(e.transferDate) = :year AND MONTH(e.transferDate) = :month")
    List<EmployeeSalaryManagement> findByEmployeeIdAndYearAndMonth(@Param("employeeId") Long employeeId,
                                                                   @Param("year") Integer year,
                                                                   @Param("month") Integer month);

    @Query("SELECT e FROM EmployeeSalaryManagement e WHERE e.employeeId = :employeeId AND YEAR(e.transferDate) = :year AND MONTH(e.transferDate) = :month")
    Page<EmployeeSalaryManagement> findByEmployeeIdAndYearAndMonth(@Param("employeeId") Long employeeId,
                                                                   @Param("year") Integer year,
                                                                   @Param("month") Integer month,
                                                                   Pageable pageable);
}