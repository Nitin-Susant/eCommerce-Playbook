package com.ebook.serviceImplimentation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebook.dto.AdminBookDTO;
import com.ebook.dto.GenreDTO;
import com.ebook.entity.Book;
import com.ebook.entity.Genre;
import com.ebook.entity.Users;
import com.ebook.repositery.BookRepositery;
import com.ebook.repositery.GenreRepositery;
import com.ebook.service.EmployeeBookService;
import com.ebook.service.UserService;
import com.ebook.utill.Conveters;

@Service
public class AdminBookServiceImpl implements EmployeeBookService {

	@Autowired
	GenreRepositery genre;

	@Autowired
	UserService userService;
	
	private final BookRepositery bookRepository;

	public AdminBookServiceImpl(BookRepositery bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public Book addBook(AdminBookDTO bookdto,String email) {

		Set<Genre> geners = new HashSet<>(genre.findByNameIn(new ArrayList<>(bookdto.getGenres())));

		Users user = userService.findByEmail(email);
		
		Book book = Conveters.AdminBookDTOtobook(bookdto);

		book.setAddedByAdmin(user);
		book.setGenre(geners);
		book.setCreatedDate(LocalDate.now());
		Book saveed = bookRepository.save(book);
		return saveed;

	}

	@Override
	public Optional<AdminBookDTO> getBookById(Long id) {
		Optional<Book> byId = bookRepository.findById(id);

		Book book = byId.get();
		AdminBookDTO adminBookDTO = new AdminBookDTO(book);

		return Optional.ofNullable(adminBookDTO);

	}

	@Override
	public List<AdminBookDTO> getAllBooks() {
		List<Book> all = bookRepository.findAll();
		List<AdminBookDTO> bookDTOs = new ArrayList<>();

		all.forEach(e -> {
			AdminBookDTO bookDTO = new AdminBookDTO(e);

			bookDTOs.add(bookDTO);
		});

		all = null;

		return bookDTOs;
	}

	@Override
	public void updateBook(AdminBookDTO updatedBook) {
		Optional<Book> optionalBook = bookRepository.findById(updatedBook.getId());

		Book book = Conveters.AdminBookDTOtobook(updatedBook);

		Set<Genre> geners = new HashSet<>(genre.findByNameIn(new ArrayList<>(updatedBook.getGenres())));

		book.setGenre(geners);
		book.setAddedByAdmin(optionalBook.get().getAddedByAdmin());
		book.setCreatedDate(optionalBook.get().getCreatedDate());

		bookRepository.save(book);

	}

	public void deleteById(Long id) {
		bookRepository.deleteById(id);
	}

	 public List<GenreDTO> getAllGenres() {
	        List<Genre> genres = genre.findAll();
	       
	        List<GenreDTO> genreDTOs = new ArrayList<>();
	        
	        for (Genre genre : genres) {
	        	GenreDTO genreDTO = new GenreDTO(genre);
	        	genreDTOs.add(genreDTO);
			}
	        
	        return genreDTOs;
	    }
	 public GenreDTO getGenresByid(Long id) {
	        Optional<Genre> genr = genre.findById(id);
	        GenreDTO genreDTO = new GenreDTO(genr.get());
	        return genreDTO;
	        
	 }
		public Genre saveGenre(GenreDTO genr) {

			Genre gen = new Genre(genr.getId(), genr.getName());

			return genre.save(gen);
		}
	
	// Implement other methods for managing books
}