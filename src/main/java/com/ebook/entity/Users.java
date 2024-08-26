package com.ebook.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor 
@NoArgsConstructor
@Entity
public class Users {
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String email;
	
	private String password;
	
	private String username;
	
	  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	  private Cart cart;
	
	   @Column(nullable = false)
	    private boolean enabled; // Flag indicating if user account is active

	   @ToString.Exclude
	    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    private Employees employee;
	   @ToString.Exclude
	    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	    private Admins admin;
	
	 @ManyToMany(fetch = FetchType.EAGER)
	 @JoinTable(name = "users_roles",
	            joinColumns = @JoinColumn(name = "user_id"),
	            inverseJoinColumns = @JoinColumn(name = "role_id"))
	   private Set<Roles> roles = new HashSet<>();
	
	
	 @ToString.Exclude
	 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	 private List<Address> addresses;

}
