package com.ets.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.ets.model.AdminAttendanceRecords;

public interface AdminAttendanceRecordRepository
        extends JpaRepository<AdminAttendanceRecords, Long> {

    Optional<AdminAttendanceRecords> findByNameAndDate(String name, LocalDate date);

    List<AdminAttendanceRecords> findByDate(LocalDate date);

    @Query("SELECT a FROM AdminAttendanceRecords a WHERE a.name = :name ORDER BY a.date desc")
    List<AdminAttendanceRecords> findByNameOrderByDateDesc(String name);

    List<AdminAttendanceRecords> findByNameAndDateBetween(String name, LocalDate from, LocalDate to);
}