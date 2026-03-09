package com.ets.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.ets.model.Attendance;
import com.ets.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@CrossOrigin
public class AttendanceController {

    private final AttendanceService service;

    
    @PostMapping("/check-in")
    public ResponseEntity<String> checkIn(@RequestParam String email) {
        String response = service.checkIn(email);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/check-out")
    public ResponseEntity<String> checkOut(@RequestParam String email) {
        String response = service.checkOut(email);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/weekly")
    public List<Attendance> weekly(@RequestParam String email) {
        return service.weeklyHistory(email);
    }
    
    
    @GetMapping("/all")
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        return ResponseEntity.ok(service.getAllAttendance());
    }
    

}

