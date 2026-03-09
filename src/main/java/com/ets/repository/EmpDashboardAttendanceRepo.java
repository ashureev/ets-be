package com.ets.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ets.model.EmpDashboardAttendance;

public interface EmpDashboardAttendanceRepo extends JpaRepository<EmpDashboardAttendance, Long> {

//	Collection<com.ets.dto.DayValueDTO> findByEmployeeIdAndDateBetween(Long empId, LocalDate start, LocalDate end);

	   List<EmpDashboardAttendance> findByEmployeeIdAndDateBetween(
	            Long employeeId,
	            LocalDate start,
	            LocalDate end
	    );
	
}
