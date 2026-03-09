package com.ets.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminAttendanceDashboardRowDto {
    private String name;
    private String department;
    private String todayStatus;        // NOT_MARKED / PRESENT / ABSENT / LEAVE
    private List<String> quickHistory; // last N statuses
}