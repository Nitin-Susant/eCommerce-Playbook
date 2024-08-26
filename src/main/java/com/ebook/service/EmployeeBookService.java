package com.ebook.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ebook.dto.AdminBookDTO;
import com.ebook.dto.GenreDTO;
import com.ebook.entity.Book;
import com.ebook.entity.Genre;
import com.ebook.entity.Users;

public interface EmployeeBookService {
    
	  Book addBook(AdminBookDTO book,String email); 
	  Optional <AdminBookDTO> getBookById(Long id);
	void  deleteById(Long id);
	  List<AdminBookDTO> getAllBooks();
	  public void updateBook( AdminBookDTO updatedBook) ;
	  public List<GenreDTO> getAllGenres();
	  public Genre saveGenre(GenreDTO genr);
	  public GenreDTO getGenresByid(Long id);
}
