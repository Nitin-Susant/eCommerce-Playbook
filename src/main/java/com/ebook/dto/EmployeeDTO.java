package com.ebook.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

	private Long id;
	private String employeeId;
	private String firstName;
	private String lastName;
	private String middleName;
	private LocalDate dateOfBirth;
	private String jobTitle;
	private String phoneNumber;
	private Double baseSalary;
	private String currency;
	private LocalDate hireDate;
	private String username; // Optional: Include username if relevant
	private String role;
	// Getters and setters (omitted for brevity)

	// Optional: Include builder pattern for convenience (omitted for brevity)
}
