package com.ebook.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebook.entity.Employees;
import com.ebook.entity.Users;
import java.util.List;



public interface UserRepository extends JpaRepository<Users, Integer> {

	  Users findByEmail(String email);

	  Long findIdByEmail(String email);
		
	
	
	
}
