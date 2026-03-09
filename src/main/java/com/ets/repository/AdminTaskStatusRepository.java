package com.ets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ets.model.AdminTaskStatus;

@Repository
public interface AdminTaskStatusRepository extends JpaRepository<AdminTaskStatus, Long> {

}
