package com.ebook.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;

import com.ebook.dto.BookFilterDTO;
import com.ebook.dto.UserBookDTO;
import com.ebook.entity.Book;

public interface UserBookService {
    
	
	public List<UserBookDTO> getLimitedBooks(int limit);
	public boolean addToCart(  Long productId , String email);
	
    List<UserBookDTO> getAllBook();
    Optional<UserBookDTO> getBookById(Long id);
    List<UserBookDTO> searchBooks(String query);
    public  List<UserBookDTO> getBooksByGenre(String genreName);
    public  List<UserBookDTO> getLimitBooksByGenre(int limit ,String genreName);
    
    Set<String> AllGenersNames();
    
 
   
    List<UserBookDTO> getRecentlyAddedBook(int limit);
    // Other methods for managing books

	public  List<UserBookDTO> findBooksByCriteria(BookFilterDTO filterDTO);
}