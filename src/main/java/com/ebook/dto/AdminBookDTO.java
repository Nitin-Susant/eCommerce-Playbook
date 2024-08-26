package com.ebook.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.ebook.entity.Book;
import com.ebook.entity.Genre;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminBookDTO {

	private Long id;
	private String title;
	private String author;
	private String isbn;
	private String imageLink;
	private double price;

	private String description;
	 private String language;
	 private String createdDate;
	private Integer pageNumber;
	private String addedByAdminUsername;
	private Set<String> genres;

	// Getters and setters
	// Constructor
	// Other methods as needed

	// Constructor to convert from Book entity to AdminBookDTO
	public AdminBookDTO(Book book) {
		this.id = book.getId();
		this.title = book.getTitle();
		this.author = book.getAuthor();
		this.isbn = book.getIsbn();
		this.imageLink = book.getImageLink();
		this.price = book.getPrice();
		this.addedByAdminUsername = book.getAddedByAdmin().getEmail();
		this.description = book.getDescription();
		this.pageNumber = book.getPageNumber();
		this.language = book.getLanguage();
		this.createdDate = book.getCreatedDate().toString();
		this.genres = book.getGenre().stream().map(Genre::getName) // Map each Genre object to its name
				.collect(Collectors.toSet());
	}

}
