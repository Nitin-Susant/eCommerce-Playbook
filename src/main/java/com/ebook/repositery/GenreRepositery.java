package com.ebook.repositery;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebook.entity.Book;
import com.ebook.entity.Genre;

public interface GenreRepositery extends JpaRepository<Genre, Long> {

	
	 Set<Genre> findByNameIn(List<String> names);
	 Genre findByName(String names);

	 @Query("SELECT g.name FROM Genre g")
	 Set<String> findAllNames();
	 
	 
	
}
