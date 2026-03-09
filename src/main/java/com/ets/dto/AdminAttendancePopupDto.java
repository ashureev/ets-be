package com.ets.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminAttendancePopupDto {
    private String name;
    private String department;
    private String date;          // yyyy-MM-dd
    private String todayStatus;   // NOT_MARKED/PRESENT/ABSENT/LEAVE
    private String loginTime;     // HH:mm:ss or null
    private String logoutTime;    // HH:mm:ss or null

    private double lastWeekScore; // %
    private int absenceCount;

    private int monthPresentDays;
    private int monthAbsentDays;
    private double monthScore;    // %
}