package com.ets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ets.model.CodingProgram;

@Repository
public interface CodingProgramRepository extends JpaRepository<CodingProgram, Long> {
	

}
