package com.ebook.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String fullName;
	    private String addressLine1;
	    private String addressLine2;
	    private String city;
	    private String state;
	    private String postalCode;
	   
	    private String phoneNumber;
	  

	    @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private Users user;
	
	    @ToString.Exclude
		 @OneToMany(mappedBy = "addresh", cascade = CascadeType.ALL)
		 private List<Order> orders;
	
	
}
