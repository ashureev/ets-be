package com.ets.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ets.dto.AdminAttendanceDashboardRowDto;
import com.ets.dto.AdminAttendanceMarkRequest;
import com.ets.dto.AdminAttendancePopupDto;
import com.ets.model.AdminAttendanceRecords;
import com.ets.service.AdminAttendanceRecordService;

@RestController
@RequestMapping("/api/admin/attendance")
@CrossOrigin
public class AdminAttendanceRecordController {

    private final AdminAttendanceRecordService service;

    public AdminAttendanceRecordController(AdminAttendanceRecordService service) {
        this.service = service;
    }

    // GET /api/admin/attendance/dashboard?date=2026-03-03&historyDays=7
    @GetMapping("/dashboard")
    public List<AdminAttendanceDashboardRowDto> dashboard(
            @RequestParam(required = false) String date,
            @RequestParam(defaultValue = "7") int historyDays
    ) {
        return service.dashboard(date, historyDays);
    }

    // POST /api/admin/attendance/mark
    @PostMapping("/mark")
    public AdminAttendanceRecords mark(@RequestBody AdminAttendanceMarkRequest req) {
        return service.mark(req);
    }

    // GET /api/admin/attendance/popup?name=basi&date=2026-03-03
    @GetMapping("/popup")
    public AdminAttendancePopupDto popup(
            @RequestParam String name,
            @RequestParam(required = false) String date
    ) {
        return service.popup(name, date);
    }
}