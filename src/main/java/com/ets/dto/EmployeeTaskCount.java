package com.ets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTaskCount {

	private int newRequest;
	private int completedRequest;
	private int pendingRequest;
	private int failedRequest;
	
}
