package com.ets.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ets.model.EmpDashboardCodingperform;

public interface EmpDashboardCoding extends JpaRepository<EmpDashboardCodingperform, Long> {

//	Collection<DayValueDTO> findByEmployeeIdAndDateBetween(Long empId, LocalDate start, LocalDate end);

	 List<EmpDashboardCodingperform> findByEmployeeIdAndDateBetween(
	            Long employeeId,
	            LocalDate start,
	            LocalDate end
	    );
}
