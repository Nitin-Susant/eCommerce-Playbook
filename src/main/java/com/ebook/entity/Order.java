package com.ebook.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringExclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity 
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter
public class Order {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private Users user;

	    @ToString.Exclude
	    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // For cascading persistence
	    private List<OrderItem> orderItems;

	    @Column(nullable = false)
	    private BigDecimal totalAmount;
	    @Column(nullable = false)
	    private String paymentStatus;

	    @Column(name = "date")
	    private LocalDate createdDate;
	
	    @ManyToOne
	    @JoinColumn(name = "addresh", nullable = false)
	    private Address addresh;
	
}

 enum OrderStatus {
    PENDING,
    PLACED,
    SHIPPED,
    DELIVERED,
    CANCELED
}
