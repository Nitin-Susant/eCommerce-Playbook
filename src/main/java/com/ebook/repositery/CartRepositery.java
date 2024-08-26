package com.ebook.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebook.entity.Cart;

public interface CartRepositery extends JpaRepository<Cart, Long> {

}
