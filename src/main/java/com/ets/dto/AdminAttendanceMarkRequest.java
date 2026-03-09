package com.ets.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.ets.enums.AttendenceStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminAttendanceMarkRequest {
    private String name;
    private String department; // optional (can be auto-filled)
    private LocalDate date;    // optional
    private AttendenceStatus status;
    private LocalTime loginTime;
    private LocalTime logoutTime;
}