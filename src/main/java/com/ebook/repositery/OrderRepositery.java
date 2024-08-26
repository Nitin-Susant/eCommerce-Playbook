package com.ebook.repositery;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebook.entity.Order;
import com.ebook.entity.Users;

public interface OrderRepositery extends JpaRepository<Order, Long> {

	public Optional<Order> findByUserAndId(Users user, Long id);
	public List<Order> findByUser(Users user);
	
}
