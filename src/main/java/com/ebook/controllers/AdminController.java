package com.ebook.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebook.dto.AdminBookDTO;
import com.ebook.dto.EmployeeDTO;
import com.ebook.dto.UsersDTO;
import com.ebook.entity.Employees;
import com.ebook.entity.Genre;
import com.ebook.entity.Roles;
import com.ebook.entity.Users;
import com.ebook.repositery.GenreRepositery;
import com.ebook.repositery.RolesRepositrry;
import com.ebook.service.EmployeeBookService;
import com.ebook.service.EmployeeService;
import com.ebook.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/books")
public class AdminController {

	@Autowired
	EmployeeBookService bookService;

	@Autowired
	UserService userService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	GenreRepositery genre;

	@Autowired
	RolesRepositrry rolesRepositrry;

	@GetMapping("/getBook/{id}")
	public String showBook(@PathVariable("id") Long id, Model model) {
		// Retrieve book by ID and add it to the model
		Optional<AdminBookDTO> book = bookService.getBookById(id);
		model.addAttribute("book", book.get());
		return "Admine/bookShow"; // Use appropriate Thymeleaf template for edit form
	}

	@GetMapping("/getallBooks")
	public String getAllBooks(Model model) {
		List<AdminBookDTO> books = bookService.getAllBooks();
		model.addAttribute("books", books);

		return "Admine/BookList";
	}

	@ModelAttribute("allGenres")
	public List<String> getGeners() {
		List<Genre> all = genre.findAll();
		List<String> gens = new ArrayList<>();
		all.forEach(e -> {
			gens.add(e.getName());
		});
		all = null;
		return gens;

	}

	// Employee

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("employee", new UsersDTO());
		return "Admine/register"; // This will return the register.html template
	}

	@PostMapping("/registerprocess")
	public String registerEmployee(@ModelAttribute("employee") UsersDTO user, HttpSession session) {

		System.out.println(user);

		UsersDTO registerUser = employeeService.registerUser(user);
		if (registerUser != null) {
			// Save relevant user information in the session
			session.setAttribute("loggedInUser", registerUser.getEmail()); // Example: store username

			// Optional: Set session timeout for security (e.g., 30 minutes)
			session.setMaxInactiveInterval(30 * 60); // 30 minutes in seconds
		} else {
			// Handle invalid registration (e.g., redirect to error page with message)
			return "redirect:/admin/books/register";
		}
		return "redirect:/admin/books/employeeform"; // Redirect to confirmation page
	}

	@GetMapping("/employeeform")
	public String showEmployeeDetailsForm(Model model) {

		List<String> allRoles = rolesRepositrry.findAlls();

		List<String> filteredRoles = allRoles.stream().filter(role -> !role.equalsIgnoreCase("ADMIN"))
				.collect(Collectors.toList());
		allRoles = null;

		model.addAttribute("roles", filteredRoles);
		EmployeeDTO e = new EmployeeDTO();
		e.setFirstName("sdfsf");
		model.addAttribute("employee", e);
		return "Admine/employeeForm"; // Thymeleaf template name
	}

	@PostMapping("/employeeformprocess")
	public String registerEmployee(@ModelAttribute("employee") EmployeeDTO employeeDTO, HttpSession session) {

		String email = (String) session.getAttribute("loggedInUser");
		System.out.println(email);
		System.out.println(employeeDTO);

		// employee.setUser(byEmail); // Associate employee with the registered user

		employeeService.registerEmployee(employeeDTO, email);
//		  session.invalidate();
		session.removeAttribute("loggedInUser");
		return "redirect:/admin/books/register"; // Redirect to completion page
	}

	@GetMapping("/getallemployees")
	public String getAllEmployees(Model model) {
		List<EmployeeDTO> employees = employeeService.getAllEmployees();
		model.addAttribute("employees", employees);
		return "Admine/allEmployees";
	}

//    @PostMapping("/register")
//    public String registerEmployee(@ModelAttribute Employee employee, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "registration_form"; // Return with validation errors
//        }
//        employeeService.registerEmployee(employee);
//        return "redirect:/employees/success"; // Redirect to success page
//    }

	@PostMapping("/registerProcess")
	public String registerUser(@ModelAttribute("user") UsersDTO user) {
		userService.registerUser(user);
		return "redirect:/admin/books/register"; // Redirect to login page after successful registration
	}

	// end Employee

}
