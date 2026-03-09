package com.ets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ets.model.AdminEmployee;

@Repository
public interface AdminEmployeeRepository extends JpaRepository<AdminEmployee, Long> {
	long countByActiveTrue();


}
