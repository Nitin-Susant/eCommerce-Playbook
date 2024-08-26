package com.ebook.dto;

import com.ebook.entity.Genre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDTO {
    private Long id;
    private String name;

    
    public  GenreDTO(Genre g) {
		this.id = g.getId();
		this.name = g.getName();
	}
    
    
    // Getters and setters
    // Constructor
    // Other methods as needed
}
