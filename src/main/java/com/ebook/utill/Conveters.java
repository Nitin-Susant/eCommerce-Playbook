package com.ebook.utill;

import java.util.HashSet;
import java.util.Set;

import com.ebook.dto.AdminBookDTO;
import com.ebook.dto.EmployeeDTO;
import com.ebook.dto.UserBookDTO;
import com.ebook.entity.Book;
import com.ebook.entity.Employees;
import com.ebook.entity.Genre;

public class Conveters {

	public static UserBookDTO bookToBookInfo(Book book) {

		UserBookDTO bookInfo = new UserBookDTO();
		bookInfo.setTitle(book.getTitle());
		bookInfo.setId(book.getId());
		bookInfo.setIsbn(book.getIsbn());
		bookInfo.setAuthor(book.getAuthor());
		bookInfo.setImageLink(book.getImageLink());
		bookInfo.setPrice(book.getPrice());
		bookInfo.setDescription(book.getDescription());
		bookInfo.setPageNumber(book.getPageNumber());
		bookInfo.setLanguage(book.getLanguage());
		Set<Genre> genre = book.getGenre();

		Set<String> geners = new HashSet<>();
		genre.forEach(e -> {
			geners.add(e.getName());
		});
		bookInfo.setGenres(geners);
		genre = null;
		return bookInfo;
	}

	public static AdminBookDTO bookToAdminBookDTO(Book book) {

		AdminBookDTO bookInfo = new AdminBookDTO();
		bookInfo.setId(book.getId());
		bookInfo.setAuthor(book.getAuthor());
		bookInfo.setImageLink(book.getImageLink());
		bookInfo.setPrice(book.getPrice());
		bookInfo.setTitle(book.getTitle());
		return bookInfo;
	}
	
	public static EmployeeDTO convert(Employees employee) {
	    if (employee == null) {
	        return null;
	    }
	    EmployeeDTO employeeDTO = new EmployeeDTO();
	    employeeDTO.setId(employee.getId());
	    employeeDTO.setEmployeeId(employee.getEmployeeId());
	    employeeDTO.setFirstName(employee.getFirstName());
	    employeeDTO.setLastName(employee.getLastName());
	    employeeDTO.setMiddleName(employee.getMiddleName());
	    employeeDTO.setDateOfBirth(employee.getDateOfBirth());
	    employeeDTO.setJobTitle(employee.getJobTitle());
	    employeeDTO.setPhoneNumber(employee.getPhoneNumber());
	    employeeDTO.setBaseSalary(employee.getBaseSalary());
	    employeeDTO.setCurrency(employee.getCurrency());
	    employeeDTO.setHireDate(employee.getHireDate());
	    employeeDTO.setUsername(employee.getUser() != null ? employee.getUser().getUsername() : null);
	    return employeeDTO;
	}

	 public static Employees convert(EmployeeDTO employee) {
	        if (employee == null) {
	            return null;
	        }
	        Employees employeeDTO = new Employees();
	        
	        employeeDTO.setId(employee.getId());
		    employeeDTO.setEmployeeId(employee.getEmployeeId());
		    employeeDTO.setFirstName(employee.getFirstName());
		    employeeDTO.setLastName(employee.getLastName());
		    employeeDTO.setMiddleName(employee.getMiddleName());
		    employeeDTO.setDateOfBirth(employee.getDateOfBirth());
		    employeeDTO.setJobTitle(employee.getJobTitle());
		    employeeDTO.setPhoneNumber(employee.getPhoneNumber());
		    employeeDTO.setBaseSalary(employee.getBaseSalary());
		    employeeDTO.setCurrency(employee.getCurrency());
		    employeeDTO.setHireDate(employee.getHireDate());

	      
	        return employeeDTO;
	    }
	
	

	public static Book AdminBookDTOtobook(AdminBookDTO book) {

		Book bookInfo = new Book();
		bookInfo.setId(book.getId());
		bookInfo.setAuthor(book.getAuthor());
		bookInfo.setImageLink(book.getImageLink());
		bookInfo.setPrice(book.getPrice());
		bookInfo.setTitle(book.getTitle());
		bookInfo.setIsbn(book.getIsbn());
		bookInfo.setLanguage(book.getLanguage());
		bookInfo.setDescription(book.getDescription());
		bookInfo.setPageNumber(book.getPageNumber());

		return bookInfo;
	}

}
