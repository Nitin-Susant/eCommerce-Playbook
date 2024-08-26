package com.ebook.controllers;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
import com.ebook.entity.Address;
import com.ebook.entity.Book;
import com.ebook.entity.Cart;
import com.ebook.entity.CartItem;
import com.ebook.entity.Order;
import com.ebook.entity.OrderItem;
import com.ebook.entity.Users;
import com.ebook.repositery.GenreRepositery;
import com.ebook.service.UserBookService;
import com.ebook.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/public")
public class UserBookController {

	@Autowired
	private UserBookService bookService;

	@Autowired
	UserService userService;

 
	@GetMapping("/orders")
	public String getallOrder( Principal principal, Model model) {
		
		Users user = userService.findByEmail(principal.getName());
		List<Order> allOders = userService.getAllOders(user);
		 
		 
		model.addAttribute("orders", allOders);
		return "Public/order";
	}
	
	
	@GetMapping("/saveorder")
	public String orderSave(@RequestParam("addersId") Long id, Model model, Principal principal, HttpSession session) {

		if (principal == null) {
			return "redirect:/public/logins";
		}

		System.out.println("+++====================");

		Order saveOrder = userService.saveOrder(principal.getName(), id);
		session.setAttribute("OrderId", saveOrder.getId());
		session.setMaxInactiveInterval(30 * 60);

		return "redirect:/public/orderoverview"; // Replace "address" with your actual template name
	}

	@GetMapping("/orderoverview")
	public String orderOverview(HttpSession session, Principal principal, Model model) {

		Long orderId = (Long) session.getAttribute("OrderId");

		System.out.println(orderId + "      ===========");

		if (principal == null) {
			return "redirect:/public/logins";
		}

		Users user = userService.findByEmail(principal.getName());
		Order oders = userService.getOders(user, orderId);

		Address addresh = oders.getAddresh();
		BigDecimal totlePrice = oders.getTotalAmount();

		List<OrderItem> orderItems = oders.getOrderItems();

		model.addAttribute("cartItems", orderItems);

		model.addAttribute("address", addresh);
		model.addAttribute("totlePrice", totlePrice);

		return "Public/overview";
	}

	@GetMapping("/payment")
	public String payment(HttpSession session, Principal principal, Model model) {

		Long orderId = (Long) session.getAttribute("OrderId");

		if (principal == null) {
			return "redirect:/public/logins";
		}

		Users user = userService.findByEmail(principal.getName());
		boolean paymentsucess = userService.paymentsucess(user, orderId);
		if (paymentsucess) {
			return "redirect:/public/getcart";
		}

		return "redirect:/public/orderoverview";
	}

	@GetMapping("/checkout")
	public String checkout(Model model, Principal principal) {

		if (principal == null) {
			return "redirect:/public/logins";
		}

		Users user = userService.findByEmail(principal.getName()); // Assuming you have UserRepository

		List<Address> shippingAddress = user.getAddresses(); // Get user's address

		System.out.println(shippingAddress);
		if (shippingAddress.size() == 0) {
			// User has no address, redirect to address form
			return "redirect:/public/add"; // Replace "address/add" with your address form path
		}

		model.addAttribute("addresses", shippingAddress);
		return "Public/addreshList";

	}

	@GetMapping("/selectAddress")
	public String selectAddresh(@RequestParam("addre") Long id, Principal principal) {

		if (principal == null) {
			return "redirect:/public/logins";
		}

		return "sdf";

	}

	@GetMapping("/add")
	public String showAddressForm(Model model, Principal principal) {

		if (principal == null) {
			return "redirect:/public/logins";
		}

		model.addAttribute("address", new Address());
		return "Public/addresh"; // Replace "address" with your actual template name
	}

	@PostMapping("/saveAddress")
	public String saveAddress(Address address, Principal principal) {

		if (principal == null) {
			return "redirect:/public/logins";
		}

		System.out.println(address);

		Address saveAddress = userService.saveAddress(address, principal.getName());

		return "redirect:/public/success"; // Or any success page after saving
	}

	@GetMapping("/success")
	public String showsucess(Model model) {

		return "Public/sucess"; // Replace "address" with your actual template name
	}

	@GetMapping("/getcart")
	public String addTocartPage(Model model, Principal principal) {

		if (principal == null) {
			return "redirect:/public/logins";
		}

		String name = principal.getName();

		Users byEmail = userService.findByEmail(name);

		Cart cartItems = byEmail.getCart();
		if (cartItems != null) {
			List<CartItem> cartItems2 = cartItems.getCartItems();

			model.addAttribute("cartItems", cartItems2);
			model.addAttribute("totalAmount", byEmail.getCart().getTotalPrice());
		}

		return "Public/addToCart";
	}

	@ResponseBody
	@PostMapping("/deleteitem/{id}")
	public String deleteitemcart(@PathVariable("id") Integer id ) {
	
		System.out.println(id+"        ===========");
		return id+"";
	}
	
	@GetMapping("/addtocart/{id}")

	public String addToCart(@PathVariable("id") Long id, Principal principal) {
		if (principal == null) {

			return "redirect:/public/logins";
		}
		bookService.addToCart(id, principal.getName());

		return "redirect:/public/getcart";
	}

	@GetMapping("/booksBygener")
	public String allBooks(@RequestParam("gener") String gener, Model m) {

		List<UserBookDTO> booksByGenre = null;

		if (gener.equals("allbooks")) {
			booksByGenre = bookService.getAllBook();
		} else if (gener.equals("recent")) {
			booksByGenre = bookService.getRecentlyAddedBook(10);
		} else {
			booksByGenre = bookService.getBooksByGenre(gener);
		}

		m.addAttribute("books", booksByGenre);

		return "Public/showAllBooks";
	}

	@GetMapping("/books")
	public String getAllBooks(Model model, Principal principal, HttpSession session) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (principal != null) {
			// If user is )authenticated, pass the username to the view
			session.setAttribute("loggedIn", true);
			session.setAttribute("username", auth.getName());

		} else {
			session.setAttribute("loggedIn", false);
		}

		List<UserBookDTO> allBook = bookService.getLimitedBooks(4);

		HashMap<String, List<UserBookDTO>> booksbyGeners = new HashMap<>();

		Set<String> allGenersNames = bookService.AllGenersNames();

		for (String string : allGenersNames) {

			List<UserBookDTO> GenerBooks = bookService.getLimitBooksByGenre(4, string);
			booksbyGeners.put(string, GenerBooks);
		}

		List<UserBookDTO> recentlyAddedBook = bookService.getRecentlyAddedBook(4);

		model.addAttribute("recentlyAddedBook", recentlyAddedBook);
		model.addAttribute("books", allBook);
		model.addAttribute("bookByGener", booksbyGeners);

//		model.addAttribute("filter", new BookFilterDTO());

		return "Public/home";
	}

	@Autowired
	GenreRepositery genre;

	@ModelAttribute("genres")
	public Set<String> getGeners() {
		Set<String> all = genre.findAllNames();
		System.out.println(all + "=================");
		return all;

	}

	@ModelAttribute("filter")
	public BookFilterDTO name(Model model) {

		BookFilterDTO modl = new BookFilterDTO();

		return modl;
	}

	@GetMapping("/filteredBooks")
	public String getFilteredBooks(BookFilterDTO filterDTO, Model model) {

		System.out.println(filterDTO.getAuthor());
		System.out.println(filterDTO.getGenre());
		System.out.println(filterDTO.getLanguage());
		System.out.println(filterDTO.getMinprice());
		System.out.println(filterDTO.getMaxprice());

		List<UserBookDTO> booksByCriteria = bookService.findBooksByCriteria(filterDTO);

		model.addAttribute("books", booksByCriteria);
		model.addAttribute("filter", filterDTO);
		System.out.println(booksByCriteria.size());

		return "Public/BookList";
	}

	@GetMapping("/booById/{id}")
	public String getBookById(@PathVariable("id") Long id, Model model) {
		Optional<UserBookDTO> book = bookService.getBookById(id);
		System.out.println("sdfsf" + book);
		if (book.isPresent()) {

			model.addAttribute("book", book.get());
			return "Public/bookShow";
			// Redirect to an error page
		}
		// Return the name of the Thymeleaf template for displaying book details
		model.addAttribute("book", book.get());
		return "Public/bookShow";
	}

}
