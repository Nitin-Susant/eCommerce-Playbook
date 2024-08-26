package com.ebook.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebook.dto.BookFilterDTO;
import com.ebook.dto.UserBookDTO;
import com.ebook.dto.UsersDTO;
import com.ebook.repositery.GenreRepositery;
import com.ebook.service.UserBookService;
import com.ebook.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/public")
public class PublicController {

	@Autowired
	UserService us;

	@Autowired
	UserBookService ubs;

	@Autowired
	GenreRepositery genre;

	@GetMapping("/home")
	public String home(Model model) {
		// Add logic to fetch featured or trending eBooks and pass them to the view
		return "Public/home1";
	}

	@ModelAttribute("genres")
	public Set<String> getGeners() {
		Set<String> all = genre.findAllNames();
		System.out.println(all +"=================");
		return all;

	}

	@ModelAttribute("filter")
	public BookFilterDTO name(Model model) {

		BookFilterDTO modl = new BookFilterDTO();

		return modl;
	}

	@GetMapping("/searchbook")
	public String searchBook(@RequestParam("query") String str, Model m) {

		List<UserBookDTO> searchBooks = ubs.searchBooks(str);
		System.out.println("=================================");
		m.addAttribute("books", searchBooks);
		return "Public/showAllBooks";
	}

	@GetMapping("/ebooks")
	public String browseEbooks(Model model) {
		// Add logic to fetch all eBooks and pass them to the view
		return "ebooks";
	}

	@GetMapping("/ebooks/{id}")
	public String viewEbook(@PathVariable Long id, Model model) {
		// Add logic to fetch eBook details by id and pass them to the view
		return "ebook_details";
	}

	@GetMapping("/search")
	public String searchEbooks(@RequestParam("query") String query, Model model) {
		// Add logic to search eBooks by query and pass search results to the view
		return "search_results";
	}

	@GetMapping("/register")
	public String registerForm(Model m) {

		UsersDTO user = new UsersDTO();
		user.setEmail("manash");

		m.addAttribute("user", user);

		return "Auth/register";
	}

	@PostMapping("/registerProcess")
	public String registerUser(@ModelAttribute("user") UsersDTO userDTO, Model model) {
		// Validate input data (e.g., check for empty fields, valid email format)

		// Check if the username or email is already registered
		System.out.println(userDTO);
		us.registerUser(userDTO);

		// Create a new user
//        userService.registerUser(userDTO);

		// Redirect to login page or home page
		return "redirect:/public/logins";
	}

	@GetMapping("/logins")
	public String loginForm() {

		return "Auth/login";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SecurityContextHolder.getContext().setAuthentication(null); // Clear authentication context
		request.getSession().invalidate(); // Invalidate session (optional, can be configured)
		return "redirect:/public/logins"; // Redirect to login page after logout
	}

	@GetMapping("/about")
	public String about() {
		// Logic for the about page
		return "about";
	}

	@GetMapping("/contact")
	public String contact() {
		// Logic for the contact page
		return "contact";
	}

}
