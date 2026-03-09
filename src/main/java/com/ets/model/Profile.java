package com.ets.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "employee_profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;

    private String profileImage;
    private String name;
    private String designation;
    private String systemName;
    private String cohort;
    private String location;

    private String email;
    private String phone;

    // renamed to avoid 
@Column(name="employee_code")
    private String employeeCode;

    private int attendance;
    private int codingScore;
}