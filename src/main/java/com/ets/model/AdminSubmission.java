package com.ets.model;

	import jakarta.persistence.*;
	import java.time.LocalDate;

import com.ets.enums.AdminSubmissionStatus;

	@Entity
	@Table(name = "admin_submissions")
	public class AdminSubmission {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String employeeName;
	    private String taskTitle;
	    private String taskCategory;
	    private LocalDate submissionDate;

	    @Enumerated(EnumType.STRING)
	    private AdminSubmissionStatus status;

	    // getters only (as you prefer)
	    public Long getId() { return id; }
	    public String getEmployeeName() { return employeeName; }
	    public String getTaskTitle() { return taskTitle; }
	    public String getTaskCategory() { return taskCategory; }
	    public LocalDate getSubmissionDate() { return submissionDate; }
	    public AdminSubmissionStatus getStatus() { return status; }

	    public void setStatus(AdminSubmissionStatus status) {
	        this.status = status;
	    }
	}


