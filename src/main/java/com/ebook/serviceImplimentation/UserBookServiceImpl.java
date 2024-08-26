package com.ebook.serviceImplimentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ebook.dto.BookFilterDTO;
import com.ebook.dto.UserBookDTO;
import com.ebook.entity.Book;
import com.ebook.entity.Cart;
import com.ebook.entity.CartItem;
import com.ebook.entity.Genre;
import com.ebook.entity.Users;
import com.ebook.repositery.BookRepositery;
import com.ebook.repositery.CartRepositery;
import com.ebook.repositery.GenreRepositery;
import com.ebook.service.UserBookService;
import com.ebook.service.UserService;
import com.ebook.utill.Conveters;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class UserBookServiceImpl implements UserBookService {

	@Autowired
	private final BookRepositery bookRepository;

	@Autowired
	UserService userService;

	@Autowired
	GenreRepositery genreRepositery;

	@Autowired
	CartRepositery cartRepositery;

	@Autowired
	EntityManager entityManager;

	@Override
	public Set<String> AllGenersNames() {

		return genreRepositery.findAllNames();
	}

	public String correctAuther(String inputauther) {
		LevenshteinDistance levenshteinDistance = new LevenshteinDistance(10);
		List<String> allAuthors = bookRepository.findAllAuthors();
		System.out.println(allAuthors);

		int ans = Integer.MAX_VALUE;
		String auther = "";

		for (String string : allAuthors) {
			int distance = levenshteinDistance.apply(inputauther.toLowerCase(), string.toLowerCase());
			System.out.println("disatance ="+inputauther+" "+string+" "+distance);
			
			
			if (distance!=-1 && distance < ans) {
				auther = string;
				ans = distance;
			}

		}

	 		return auther;
	}

	
	public String correctBook(String inputauther) {
		LevenshteinDistance levenshteinDistance = new LevenshteinDistance(10);
		List<String> allAuthors = bookRepository.findAllAuthors();
		System.out.println(allAuthors);

		int ans = Integer.MAX_VALUE;
		String auther = "";

		for (String string : allAuthors) {
			int distance = levenshteinDistance.apply(inputauther.toLowerCase(), string.toLowerCase());
			System.out.println("disatance ="+inputauther+" "+string+" "+distance);
			
			
			if (distance!=-1 && distance < ans) {
				auther = string;
				ans = distance;
			}

		}

	 		return auther;
	}
	
	
	
	public boolean addToCart(Long productId, String email) {

		Optional<Book> bookDto = bookRepository.findById(productId);
		Book book = bookDto.get();

		Users byEmail = userService.findByEmail(email);

		Cart cart = null;
		try {
			cart = entityManager.createQuery("SELECT c FROM Cart c WHERE c.user.email = :email", Cart.class)
					.setParameter("email", email).getSingleResult();
		} catch (NoResultException e) {
			cart = new Cart();
			cart.setUser(byEmail);
			cart.setTotalPrice(0.0);
		}

		boolean flag = false;

		for (CartItem items : cart.getCartItems()) {

			if (items.getBook().getId() == book.getId()) {
				items.setQuantity(items.getQuantity() + 1);
				items.setPrice(items.getBook().getPrice() * (items.getQuantity()));
				cart.setTotalPrice(cart.getTotalPrice() + book.getPrice());
				flag = true;
				break;
			}

		}

		if (!flag) {
			CartItem newCartItem = new CartItem();
			newCartItem.setCart(cart);
			newCartItem.setBook(book);
			newCartItem.setQuantity(1);
			newCartItem.setPrice(book.getPrice());
			cart.setTotalPrice(cart.getTotalPrice() + book.getPrice());
			cart.getCartItems().add(newCartItem);

		}
		// Update total price (consider book price and quantity)

		// Persist changes to database (using JPA repository)

		Cart save = cartRepositery.save(cart);

		if (save != null) {
			return true;
		} else {
			return false;
		}

	}

	public List<UserBookDTO> findBooksByCriteria(BookFilterDTO filterDTO) {

		String author = filterDTO.getAuthor();

		
		
		Genre byName = genreRepositery.findByName(filterDTO.getGenre());
		Set<Genre> genres = null;
		if (byName != null) {
			genres = new HashSet<>();
			genres.add(byName);
		}

		String language = filterDTO.getLanguage();
		Double minPrice = filterDTO.getMinprice();
		Double maxPrice = filterDTO.getMaxprice();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Book> cq = cb.createQuery(Book.class);
		Root<Book> bookRoot = cq.from(Book.class);

		// Build predicates (optional filters)
		List<Predicate> predicates = new ArrayList<>();

		if (author != null && !author.isEmpty()) {
			String correctAuther = correctAuther(author);
			System.out.println(correctAuther+"=================");
			predicates.add(cb.like(bookRoot.get("author"), "%" + correctAuther + "%"));
		}

		if (genres != null && !genres.isEmpty()) {
			Join<Book, Genre> bookGenreJoin = bookRoot.join("genre"); // Inner join with Genre
			Set<Long> genreIds = genres.stream().map(Genre::getId).collect(Collectors.toSet()); // Convert to Set of IDs
			predicates.add(bookGenreJoin.get("id").in(genreIds)); // Filter by genre IDs

		}

		if (language != null && !language.isEmpty()) {
			predicates.add(cb.equal(bookRoot.get("language"), language));
		}

		if (minPrice != null && maxPrice != null) {
			predicates.add(cb.between(bookRoot.get("price"), minPrice, maxPrice));
		} else if (minPrice != null) {
			predicates.add(cb.greaterThanOrEqualTo(bookRoot.get("price"), minPrice));
		}

		// Combine predicates (AND by default)
		System.out.println(predicates);
		cq.select(bookRoot).where(cb.and(predicates.toArray(new Predicate[0])));

		TypedQuery<Book> query = entityManager.createQuery(cq);

		List<Book> books = query.getResultList();
		List<UserBookDTO> bookInfos = new ArrayList<>();
		for (Book book : books) {
			UserBookDTO bookInfo = Conveters.bookToBookInfo(book);

			bookInfos.add(bookInfo);
		}

		return bookInfos;

	}

	public UserBookServiceImpl(BookRepositery bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public List<UserBookDTO> getRecentlyAddedBook(int limit) {

		Pageable pageable = PageRequest.of(0, limit);
		List<Book> books = bookRepository.findRecentlyAddedBook(pageable).getContent();
		List<UserBookDTO> bookInfos = new ArrayList<>();
		for (Book book : books) {
			UserBookDTO bookInfo = Conveters.bookToBookInfo(book);

			bookInfos.add(bookInfo);
		}

		return bookInfos;

	}

	public List<UserBookDTO> getLimitedBooks(int limit) {
		Pageable pageable = PageRequest.of(0, limit);
		List<Book> books = bookRepository.findAll(pageable).getContent();
		List<UserBookDTO> bookInfos = new ArrayList<>();
		for (Book book : books) {
			UserBookDTO bookInfo = Conveters.bookToBookInfo(book);

			bookInfos.add(bookInfo);
		}

		return bookInfos;
	}

	public List<UserBookDTO> getLimitBooksByGenre(int limit, String genreName) {
		Pageable pageable = PageRequest.of(0, limit);
		System.out.println("geners start");
		List<Book> books = bookRepository.findByGenreName(genreName, pageable).getContent();
		System.out.println("geners end");
		List<UserBookDTO> bookInfos = new ArrayList<>();
		for (Book book : books) {
			UserBookDTO bookInfo = Conveters.bookToBookInfo(book);

			bookInfos.add(bookInfo);
		}
		return bookInfos;
	}

	public List<UserBookDTO> getBooksByGenre(String genreName) {
		List<Book> books = bookRepository.findByGenreName(genreName);
		List<UserBookDTO> bookInfos = new ArrayList<>();
		for (Book book : books) {
			UserBookDTO bookInfo = Conveters.bookToBookInfo(book);

			bookInfos.add(bookInfo);
		}
		return bookInfos;
	}

	@Override
	public List<UserBookDTO> getAllBook() {
		List<Book> books = bookRepository.findAll();
		List<UserBookDTO> bookInfos = new ArrayList<>();
		for (Book book : books) {
			UserBookDTO bookInfo = Conveters.bookToBookInfo(book);

			bookInfos.add(bookInfo);
		}
		return bookInfos;
	}

	@Override
	public Optional<UserBookDTO> getBookById(Long id) {
		Optional<Book> book = bookRepository.findById(id);
		UserBookDTO bookToBookInfo = Conveters.bookToBookInfo(book.get());

		return Optional.ofNullable(bookToBookInfo);
	}

	@Override
	public List<UserBookDTO> searchBooks(String query) {

		
		
		List<Book> byTitle = bookRepository.findByTitle(query.toLowerCase());
		List<UserBookDTO> bookInfos = new ArrayList<>();
		for (Book book : byTitle) {
			UserBookDTO bookInfo = Conveters.bookToBookInfo(book);

			bookInfos.add(bookInfo);
		}
		return bookInfos;
	}

 

	// Implement other methods for managing books
}