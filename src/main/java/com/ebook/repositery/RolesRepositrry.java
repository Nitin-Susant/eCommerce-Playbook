package com.ebook.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ebook.entity.Genre;
import com.ebook.entity.Roles;

public interface RolesRepositrry extends JpaRepository<Roles, Long> {

	  @Query("SELECT r.name FROM Roles r")
	  List<String> findAlls();	
	   
	  Roles findByName(String names);
	 
	
}
