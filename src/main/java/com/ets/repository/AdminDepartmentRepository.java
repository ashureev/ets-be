package com.ets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ets.model.AdminDepartment;

public interface AdminDepartmentRepository 
        extends JpaRepository<AdminDepartment, Long> {

    boolean existsByName(String name);
}