package com.ets.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ets.enums.AdminSubmissionStatus;
import com.ets.exception.ResourceNotFoundException;
import com.ets.model.AdminSubmission;
import com.ets.repository.AdminSubmissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminSubmissionService {

    private final AdminSubmissionRepository repository;

    // ✅ Create
    public AdminSubmission createSubmission(AdminSubmission submission) {
        submission.setStatus(AdminSubmissionStatus.PENDING);
        return repository.save(submission);
    }

//    
//    public AdminSubmission saveSubmission(AdminSubmission submission) {
//        submission.setStatus("PENDING"); // default status
//        return repository.save(submission);
//    }
//    
    
    // ✅ Get All
    public List<AdminSubmission> getAllSubmissions() {
        return repository.findAll();
    }

    // ✅ Count Pending
    public long countPending() {
        return repository.countByStatus(AdminSubmissionStatus.PENDING);
    }

    // ✅ Approve
    public void approveSubmission(Long id) {
        AdminSubmission submission = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Submission not found with id: " + id));

        submission.setStatus(AdminSubmissionStatus.APPROVED);
        repository.save(submission);
    }

    // ✅ Reject
    public void rejectSubmission(Long id) {
        AdminSubmission submission = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Submission not found with id: " + id));

        submission.setStatus(AdminSubmissionStatus.REJECTED);
        repository.save(submission);
    }
}