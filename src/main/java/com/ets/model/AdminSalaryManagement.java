package com.ets.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "admin_salary_management")
public class AdminSalaryManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_code", nullable = false, unique = true)
    private String employeeCode;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Column(name = "designation")
    private String designation;

    @Column(name = "department")
    private String department;

    @Column(name = "gross_salary", nullable = false)
    private BigDecimal grossSalary;

    @Column(name = "deductions", nullable = false)
    private BigDecimal deductions;

    @Column(name = "net_salary", nullable = false)
    private BigDecimal netSalary;

    @Column(name = "status")
    private String status; // GENERATED, PENDING, PAID

    @Column(name = "receipt_issued")
    private Boolean receiptIssued;

    @Column(name = "receipt_date")
    private LocalDate receiptDate;

}