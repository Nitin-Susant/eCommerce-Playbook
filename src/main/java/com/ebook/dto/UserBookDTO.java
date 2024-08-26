package com.ebook.dto;

import java.util.Set;

import lombok.Data;

@Data
public class UserBookDTO {
	private Long id;

	private String title;

	private String author;

	private String isbn;

	private String imageLink;
	
	private String description;
 
	private Integer pageNumber;
	
	 private String language;

	private double price;
	
	  private Set<String> genres;
	  
	  
}
