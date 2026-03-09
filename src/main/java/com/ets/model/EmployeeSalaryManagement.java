package com.ets.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employee_salary_management")
public class EmployeeSalaryManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;
    private String cycle;
    private LocalDate transferDate;
    private BigDecimal gross;
    private BigDecimal deductions;
    private BigDecimal netAmount;
    private String transactionStatus;
    private String viewUrl;
    private String downloadUrl;
}