package com.ets.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ets.enums.AttendenceStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "admin_attendance_record",
    uniqueConstraints = @UniqueConstraint(columnNames = {"name", "date"})
)
public class AdminAttendanceRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String department;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private AttendenceStatus status;

    private LocalTime loginTime;
    private LocalTime logoutTime;
}