package com.ebook.entity;

 

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String isbn;

    @Column(name = "image_link")
    private String imageLink;
    
    @Column(nullable = false)
    private Double price;  
    
    
    @Column(nullable = false)
    private String language;
    
    @Column(name = "created_date")
    private LocalDate createdDate; 
    
    @Column( columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "page_number")
    private Integer pageNumber;
 
    @ManyToMany(fetch = FetchType.EAGER,cascade =  CascadeType.REMOVE)
    @JoinTable (
    	
        name = "book_genre",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genre = new HashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "added_by", nullable = false)
    private Users addedByAdmin;

	 

     
}
