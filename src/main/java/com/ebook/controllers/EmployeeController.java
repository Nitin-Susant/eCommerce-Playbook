package com.ebook.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ebook.dto.AdminBookDTO;
import com.ebook.dto.GenreDTO;
import com.ebook.entity.Book;
import com.ebook.entity.Genre;
import com.ebook.entity.Users;
import com.ebook.repositery.GenreRepositery;
import com.ebook.service.EmployeeBookService;
import com.ebook.service.UserService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeBookService bookService;

	@Autowired
	UserService userService;

	@Autowired
	GenreRepositery genre;

	@GetMapping("/addBooks")
	public String addBook(Model m) {
		AdminBookDTO book = new AdminBookDTO();
		book.setLanguage("english");
		m.addAttribute("book", book);

		return "Employee/addBook";

	}

	@GetMapping("/edit/{id}")
	public String showEditBookForm(@PathVariable("id") Long id, Model model) {
		// Retrieve book by ID and add it to the model
		Optional<AdminBookDTO> book = bookService.getBookById(id);
		model.addAttribute("book", book.get());
		return "Employee/addBook"; // Use appropriate Thymeleaf template for edit form
	}

	@GetMapping("/getBook/{id}")
	public String showBook(@PathVariable("id") Long id, Model model) {
		// Retrieve book by ID and add it to the model
		Optional<AdminBookDTO> book = bookService.getBookById(id);
		model.addAttribute("book", book.get());
		return "Employee/bookShow"; // Use appropriate Thymeleaf template for edit form
	}

	@PostMapping("/saveBook")
	public String addBook(@ModelAttribute("book") AdminBookDTO book, Principal principal) {
		// add principle
		System.out.println(book.getDescription());

		if (book.getId() != null) {

			bookService.updateBook(book);

		} else {
			String email = principal.getName();
			 
			Book book2 = bookService.addBook(book, email);
		}

		return "redirect:/employee/addBooks";
	}

	@GetMapping("/delete/{id}")
	public String deleteBook(@PathVariable Long id) {

		bookService.deleteById(id);
		return "redirect:/employee/getallBooks";
	}

	@GetMapping("/getallBooks")
	public String getAllBooks(Model model) {
		List<AdminBookDTO> books = bookService.getAllBooks();
		model.addAttribute("books", books);

		return "Employee/BookList";
	}

//	geners conroolers 

	@ModelAttribute("allGenres")
	public Set<String> getGeners() {
		Set<String> all = genre.findAllNames();
		 
		return all;

	}

	@GetMapping("/addgeners")
	public String showAddForm(Model model) {
		model.addAttribute("genre", new GenreDTO());

		return "Employee/addGeners";
	}

	@GetMapping("/editGener/{id}")
	public String EditForm(@PathVariable("id") Long id, Model model) {

		GenreDTO genresByid = bookService.getGenresByid(id);
		model.addAttribute("genre", genresByid);

		return "Employee/addGeners";
	}

	@PostMapping("/savegeners")
	public String saveGeners(GenreDTO dto, Model model) {
		model.addAttribute("genre", new GenreDTO());

		System.out.println(dto);
		bookService.saveGenre(dto);

		return "redirect:/employee/genres";
	}

	@GetMapping("/genres")
	public String getAllGenres(Model model) {
		List<GenreDTO> genres = bookService.getAllGenres();
		model.addAttribute("genres", genres);
		return "Employee/allGeners";
	}

}
