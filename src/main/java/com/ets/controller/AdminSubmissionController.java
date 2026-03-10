package com.ets.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ets.model.AdminSubmission;
import com.ets.service.AdminSubmissionService;

@RestController
@RequestMapping("/api/admin/submissions")
public class AdminSubmissionController {

    private final AdminSubmissionService service;

    public AdminSubmissionController(AdminSubmissionService service) {
        this.service = service;
    }
    
    
    
    @PostMapping("/create")
    public ResponseEntity<AdminSubmission> createSubmission(
            @RequestBody AdminSubmission submission) {

        AdminSubmission saved = service.createSubmission(submission);
        return ResponseEntity.ok(saved);
    }

    // ✅ Get All
    @GetMapping("/fetch")
    public ResponseEntity<List<AdminSubmission>> getAll() {
        return ResponseEntity.ok(service.getAllSubmissions());
    }

    // ✅ Approve
    @PutMapping("/{id}/approve")
    public ResponseEntity<String> approve(@PathVariable Long id) {
        service.approveSubmission(id);
        return ResponseEntity.ok("Submission approved successfully");
    }

    // ✅ Reject
    @PutMapping("/{id}/reject")
    public ResponseEntity<String> reject(@PathVariable Long id) {
        service.rejectSubmission(id);
        return ResponseEntity.ok("Submission rejected successfully");
    }

    // ✅ Pending Count (Correct Placement)
    @GetMapping("/pending/count")
    public ResponseEntity<?> pendingCount() {
        try {
            long count = service.countPending();
            Map<String, Long> response = new HashMap<>();
            response.put("pendingCount", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Internal Error: " + e.getMessage()));
        }
    }
}


