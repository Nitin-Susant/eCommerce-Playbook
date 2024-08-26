package com.ebook.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employees {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String employeeId; // Unique identifier for the employee

	private String firstName;

	private String lastName;

	private String middleName;

	private LocalDate dateOfBirth;

	private String jobTitle;

	private String phoneNumber;

	private Double baseSalary; // Base salary amount
	private String currency; // Currency code (e.g., USD, EUR)

	@Temporal(TemporalType.DATE)
	private LocalDate hireDate;

	@JoinColumn(name = "user_id", unique = true)
	@OneToOne
	private Users user;
}
