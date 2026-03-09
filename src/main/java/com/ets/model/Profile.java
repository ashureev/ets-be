package com.ets.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
	@Table(name = "employee_profile")
	public class Profile {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @OneToOne
	    @JoinColumn(name = "employee_id", nullable = false)
	    private Employee employee;

	    private String profileImage;
	    private String name;
	    private String designation;
	    private String systemName;
	    private String cohort;
	    private String location;

	    private String email;
	    private String phone;
	    private String employeeId;

	    private int attendance;
	    private int codingScore;

}