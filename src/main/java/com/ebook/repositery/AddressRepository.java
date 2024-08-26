package com.ebook.repositery;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebook.entity.Address;
import com.ebook.entity.Users;

public interface AddressRepository extends JpaRepository< Address, Integer> {

	public Optional<Address> findByUserAndId(Users user, Long id);

		
	
	
}
