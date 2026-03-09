package com.ets.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ets.model.EmployeeLogin;

public interface EmployeeLoginRepository extends JpaRepository<EmployeeLogin, Long> {

    Optional<EmployeeLogin> findByEmailAddress(String emailAddress);

    boolean existsByEmailAddress(String emailAddress);
}