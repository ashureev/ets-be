package com.ets.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "employee_login")
public class EmployeeLogin {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	private String emailAddress;

	
	private String password;

	
	private String role;

}