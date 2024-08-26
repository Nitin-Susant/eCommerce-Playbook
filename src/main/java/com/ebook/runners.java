package com.ebook;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ebook.entity.Book;
import com.ebook.entity.Genre;
import com.ebook.entity.Roles;
import com.ebook.entity.Users;
import com.ebook.repositery.AddressRepository;
import com.ebook.repositery.BookRepositery;
import com.ebook.repositery.GenreRepositery;
import com.ebook.repositery.RolesRepositrry;
import com.ebook.repositery.UserRepository;
import com.ebook.service.UserBookService;
import com.ebook.service.UserService;

@Component
public class runners implements CommandLineRunner {

	@Autowired
	UserRepository ur;

	@Autowired
	AddressRepository ar;

	@Autowired
	BookRepositery br;

	@Autowired
	UserService us;

	@Autowired
	RolesRepositrry rr;

	@Autowired
	GenreRepositery genre;

	@Autowired
	UserBookService  ubs;

	@Override
	public void run(String... args) throws Exception {
		
	 
		
//	ubs.addToCart(8l, "laxmi@gmail.com");
		
		

//		ubs.name();
		
		
//	        List<String> matchingBooks = new ArrayList<>();
//	        String String="";
//	        // Apply fuzzy matching to find similar author names
//	        levenshteinDistance levenshteinDistance = new LevenshteinDistance();
//	        int threshold = 3; // Set a threshold for similarity
//	        
//	        for (String auther : allAuthors) {
//	            String actualAuthorName = auther;
//	            if (levenshteinDistance.apply(actualAuthorName, inputAuthorName) <= threshold) {
//	                matchingBooks.add(book);
//	            }
//	        }
//	        
//	        return matchingBooks;
//	    }

		// ur.save(user);

//		saveGen();

// 	     saveRoles();
//		
//		 us.registerUser(user);
//		 us.registerUser(user);

	}

	public void saveGen() {
		List<Genre> gen = new ArrayList<>();
		gen.add(new Genre(1l, "Horror"));
		gen.add(new Genre(2l, "Romance"));
		gen.add(new Genre(3l, "Science fiction"));
		gen.add(new Genre(4l, "Thriller"));
		gen.add(new Genre(5l, "Historical fiction"));
		gen.add(new Genre(6l, "Action and adventure"));
		gen.add(new Genre(7l, "Mystery"));
		gen.add(new Genre(8l, "Literary fiction"));
		gen.add(new Genre(9l, "Fantasy"));

		genre.saveAll(gen);
	}

	private void saveRoles() {
//		Roles userRole = new Roles();
//		userRole.setName("USER");
		Roles admineRolr = new Roles();
		admineRolr.setName("EMPLOYEE");
		List<Roles> roles = new ArrayList<>();
		roles.add(admineRolr);
//		roles.add(userRole);
		rr.saveAll(roles);
	}

	public void addBooks(Book b) {
		Users user = new Users();
		user.setId(1);
		user.setEmail("manash@gmal");
		user.setPassword("1234");
		user.setUsername("nitin");

		b.setAddedByAdmin(user);
		br.save(b);

	}

}
