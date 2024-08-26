package com.ebook.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookFilterDTO {

	
	    private String author;
	    private String genre;
	    private String language;
	    private double minprice;
	    private double maxprice;
	
	
}
