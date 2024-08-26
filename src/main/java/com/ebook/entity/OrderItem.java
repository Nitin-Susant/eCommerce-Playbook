package com.ebook.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
public class OrderItem {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "order_id", nullable = false)
	    private Order order;

	    @ManyToOne
	    @JoinColumn(name = "product_id", nullable = false)
	    private Book product;

	    private int quantity;
	    
	    @Enumerated(EnumType.STRING)
	    private OrderItemStatus status;

	    @Column(nullable = false)
	    private Double unitPrice;

	    @Column(nullable = false)
	    private BigDecimal totalPrice;
}
 enum OrderItemStatus {
    PENDING,
    SHIPPED,
    DELIVERED,
    CANCELED
}