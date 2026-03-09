package com.ets.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.dto.AdminAttendanceDashboardRowDto;
import com.ets.dto.AdminAttendanceMarkRequest;
import com.ets.dto.AdminAttendancePopupDto;
import com.ets.enums.AttendenceStatus;
import com.ets.model.AdminAttendanceRecords;
import com.ets.repository.AdminAttendanceRecordRepository;

@Service
public class AdminAttendanceRecordService {

    private final AdminAttendanceRecordRepository repository;

    public AdminAttendanceRecordService(AdminAttendanceRecordRepository repository) {
        this.repository = repository;
    }

    // Master employee list
    private static final List<Map.Entry<String, String>> MASTER_EMPLOYEES =
            Arrays.asList(
                    new AbstractMap.SimpleEntry<>("basi", "HR"),
                    new AbstractMap.SimpleEntry<>("Karan", "Design"),
                    new AbstractMap.SimpleEntry<>("Suneetha", "Engineering"),
                    new AbstractMap.SimpleEntry<>("Vikram", "Marketing"),
                    new AbstractMap.SimpleEntry<>("Anjali", "HR")
            );

    private String departmentOf(String name) {
        return MASTER_EMPLOYEES.stream()
                .filter(e -> e.getKey().equalsIgnoreCase(name))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    // ================= DASHBOARD =================
    @Transactional(readOnly = true)
    public List<AdminAttendanceDashboardRowDto> dashboard(String dateStr, int historyDays) {

        LocalDate date = (dateStr == null || dateStr.isBlank())
                ? LocalDate.now()
                : LocalDate.parse(dateStr);

        int days = (historyDays <= 0) ? 7 : historyDays;

        List<AdminAttendanceDashboardRowDto> result = new ArrayList<>();

        for (Map.Entry<String, String> emp : MASTER_EMPLOYEES) {

            String name = emp.getKey();
            String dept = emp.getValue();

            String todayStatus = repository.findByNameAndDate(name, date)
                    .map(r -> r.getStatus() == null ? "NOT_MARKED" : r.getStatus().name())
                    .orElse("NOT_MARKED");

            LocalDate from = date.minusDays(days);
            LocalDate to = date.minusDays(1);

            List<AdminAttendanceRecords> past =
                    repository.findByNameAndDateBetween(name, from, to);

            past.sort((a, b) -> b.getDate().compareTo(a.getDate()));

            List<String> quickHistory = new ArrayList<>();
            for (AdminAttendanceRecords r : past) {
                quickHistory.add(r.getStatus() == null ? "NOT_MARKED" : r.getStatus().name());
                if (quickHistory.size() == days) break;
            }

            result.add(new AdminAttendanceDashboardRowDto(
                    name, dept, todayStatus, quickHistory
            ));
        }

        return result;
    }

    // ================= MARK =================
    @Transactional
    public AdminAttendanceRecords mark(AdminAttendanceMarkRequest req) {

        LocalDate date = (req.getDate() == null) ? LocalDate.now() : req.getDate();

        // If department not sent, try master list
        String dept = (req.getDepartment() != null && !req.getDepartment().isBlank())
                ? req.getDepartment()
                : departmentOf(req.getName());

        AttendenceStatus status = (req.getStatus() == null) ? AttendenceStatus.NOT_MARKED : req.getStatus();

        AdminAttendanceRecords record = repository.findByNameAndDate(req.getName(), date)
                .orElse(new AdminAttendanceRecords());

        record.setName(req.getName());
        record.setDepartment(dept);
        record.setDate(date);
        record.setStatus(status);
        record.setLoginTime(req.getLoginTime());
        record.setLogoutTime(req.getLogoutTime());

        return repository.save(record);
    }

    // ================= POPUP =================
    @Transactional(readOnly = true)
    public AdminAttendancePopupDto popup(String name, String dateStr) {

        LocalDate date = (dateStr == null || dateStr.isBlank())
                ? LocalDate.now()
                : LocalDate.parse(dateStr);

        String dept = departmentOf(name);

        AdminAttendanceRecords today = repository.findByNameAndDate(name, date).orElse(null);

        String todayStatus = (today == null || today.getStatus() == null)
                ? "NOT_MARKED"
                : today.getStatus().name();

        String loginTime = (today != null && today.getLoginTime() != null)
                ? today.getLoginTime().toString()
                : null;

        String logoutTime = (today != null && today.getLogoutTime() != null)
                ? today.getLogoutTime().toString()
                : null;

        // Last week score (present vs absent)
        LocalDate weekFrom = date.minusDays(6);
        List<AdminAttendanceRecords> week =
                repository.findByNameAndDateBetween(name, weekFrom, date);

        int weekP = 0, weekA = 0;
        for (AdminAttendanceRecords r : week) {
            if (r.getStatus() == AttendenceStatus.PRESENT) weekP++;
            if (r.getStatus() == AttendenceStatus.ABSENT) weekA++;
        }
        double lastWeekScore = percent(weekP, weekA);

        // Month stats
        LocalDate monthFrom = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate monthTo = date.with(TemporalAdjusters.lastDayOfMonth());

        List<AdminAttendanceRecords> month =
                repository.findByNameAndDateBetween(name, monthFrom, monthTo);

        int monthP = 0, monthA = 0;
        for (AdminAttendanceRecords r : month) {
            if (r.getStatus() == AttendenceStatus.PRESENT) monthP++;
            if (r.getStatus() == AttendenceStatus.ABSENT) monthA++;
        }

        return new AdminAttendancePopupDto(
                name,
                dept,
                date.toString(),
                todayStatus,
                loginTime,
                logoutTime,
                lastWeekScore,
                monthA,
                monthP,
                monthA,
                percent(monthP, monthA)
        );
    }

    private double percent(int present, int absent) {
        int total = present + absent;
        if (total == 0) return 0.0;
        return Math.round((present * 100.0 / total) * 10.0) / 10.0;
    }
}