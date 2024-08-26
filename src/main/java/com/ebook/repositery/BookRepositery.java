package com.ebook.repositery;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebook.dto.UserBookDTO;
import com.ebook.entity.Book;

public interface BookRepositery extends JpaRepository<Book, Long> {

	Page<Book> findAll(Pageable pageable);

	Page<Book> findByGenreName(String genreName, Pageable pageable);
	
	@Query("SELECT e FROM Book e WHERE LOWER(e.title) LIKE LOWER(:title)")
	List<Book> findByTitle(String title);

	List<Book> findByGenreName(String genreName);

	@Query("SELECT b FROM Book b ORDER BY b.createdDate DESC")
	Page<Book> findRecentlyAddedBook(Pageable pageable);
	
	 @Query("SELECT DISTINCT b.author FROM Book b")
	 List<String> findAllAuthors();
	 
	  

}
