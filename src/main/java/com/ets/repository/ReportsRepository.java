package com.ets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ets.model.Reports;

@Repository
public interface ReportsRepository extends JpaRepository<Reports, Long> {
}
