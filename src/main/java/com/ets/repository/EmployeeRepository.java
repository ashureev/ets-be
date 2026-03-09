//package com.ets.repository;
//
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import com.ets.model.Attendence;
//import com.ets.model.Employee;
//
//@Repository
//public interface EmployeeRepository extends JpaRepository<Employee, Long> {
//
//    Optional<Employee> findByempId(Long empId);
//
//
//	Optional<Employee> findById(Long empId);
//
//	void deleteById(Long empId);
//
//
//	Optional<Attendence> findByEmployeename(String employeename);
//	
//
//}









package com.ets.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ets.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.username = :username")
    Optional<Employee> findByUsername(String username);

    Optional<Employee> findByEmail(String email);
}
