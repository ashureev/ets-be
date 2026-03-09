package com.ets.dto;

import java.util.List;

public record EmployeeDashboardDTO(DashboardSummaryDTO summary,
        List<DayValueDTO> attendance,
        List<DayValueDTO> performance) {

}
