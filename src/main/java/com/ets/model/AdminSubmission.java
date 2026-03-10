package com.ets.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.ets.enums.AdminSubmissionStatus;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
}
