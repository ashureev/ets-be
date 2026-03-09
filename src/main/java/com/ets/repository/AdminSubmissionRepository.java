package com.ets.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ets.enums.AdminSubmissionStatus;
import com.ets.model.AdminSubmission;

public interface AdminSubmissionRepository extends JpaRepository<AdminSubmission, Long> {

	List<AdminSubmission> findByStatus(AdminSubmissionStatus status);
	 

	    long countByStatus(AdminSubmissionStatus status);
	
}
