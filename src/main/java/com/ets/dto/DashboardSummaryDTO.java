package com.ets.dto;

public record DashboardSummaryDTO(long newTasks,
        long acceptedTasks,
        long completedTasks,
        long failedTasks) {

}
