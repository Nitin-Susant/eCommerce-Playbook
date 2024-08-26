package com.ebook.dto;

import com.ebook.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data


@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {

	private Integer id;
    private String username;
    private String email;
    private String password;

    
    public UsersDTO(Users user) {
    	this.id = user.getId();
    	this.username = user.getUsername();
    	
    	this.email = user.getEmail();
    	this.password = user.getPassword();
    }
    
    // Getters and setters
    // You can also include additional fields or validation logic as needed
}