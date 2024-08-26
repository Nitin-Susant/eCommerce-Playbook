package com.ebook.serviceImplimentation;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebook.dto.UsersDTO;
import com.ebook.entity.Address;
import com.ebook.entity.Cart;
import com.ebook.entity.CartItem;
import com.ebook.entity.Order;
import com.ebook.entity.OrderItem;
import com.ebook.entity.Roles;
import com.ebook.entity.Users;
import com.ebook.repositery.AddressRepository;
import com.ebook.repositery.OrderRepositery;
import com.ebook.repositery.RolesRepositrry;
import com.ebook.repositery.UserRepository;
import com.ebook.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

	@Autowired
	RolesRepositrry rolle;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	OrderRepositery orderRepositery;
	
	public Order getOders(Users u, Long id) {
		// Set user association for the address (assuming a user is logged in):
		Optional<Order> byId = orderRepositery.findByUserAndId(u, id);

		System.out.println(byId.get());

		return byId.get();
	}
	
	public List<Order> getAllOders(Users u) {
		// Set user association for the address (assuming a user is logged in):
		List<Order> byId = orderRepositery.findByUser(u);

		List<Order> sucess = new ArrayList<>();
		for (Order order : byId) {
			if (order.getPaymentStatus().equals("sucess")) {
				sucess.add(order);
			}
		}
		
		 System.out.println(byId);

		return sucess;
	}
	
	public boolean paymentsucess(Users u, Long id) {
		// Set user association for the address (assuming a user is logged in):
		Optional<Order> byId = orderRepositery.findByUserAndId(u, id);

		 Order order = byId.get();
		 order.setPaymentStatus("sucess");
		 Order save = orderRepositery.save(order);
		 if (save!=null) {
			return true;
		}
		 
		return false;
	}

	public Order saveOrder(String email, Long id) {

		Users user = findByEmail(email);
		Cart cart = user.getCart();
		Double totlePrice = cart.getTotalPrice();
		List<CartItem> cartItems = cart.getCartItems();
		Address address = getAddress(user, id);

		Order order = new Order();
		order.setAddresh(address);
		order.setTotalAmount(BigDecimal.valueOf(totlePrice));
		order.setUser(user);
		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItem cartItem : cartItems) {
			OrderItem oi = new OrderItem();
			oi.setProduct(cartItem.getBook());
			oi.setQuantity(cartItem.getQuantity());
			oi.setTotalPrice(BigDecimal.valueOf(cartItem.getPrice()));
			oi.setUnitPrice(cartItem.getBook().getPrice());
			oi.setOrder(order);
			orderItems.add(oi);
		}
		order.setOrderItems(orderItems);
		order.setCreatedDate(LocalDate.now());
		order.setPaymentStatus("pending");
		Order save = orderRepositery.save(order);

		return save;
	}

	public Address getAddress(Users u, Long id) {
		// Set user association for the address (assuming a user is logged in):
		Optional<Address> byId = addressRepository.findByUserAndId(u, id);

		System.out.println(byId.get());

		return byId.get();
	}

	public Address saveAddress(Address address, String email) {
		// Set user association for the address (assuming a user is logged in):
		Users currentUser = findByEmail(email);
		address.setUser(currentUser);

		Address save = addressRepository.save(address); // Assuming you have an AddressRepository

		return save;
	}

	@Override
	public Long findIdByEmail(String email) {

		Long idByEmail = userRepository.findIdByEmail(email);
		return idByEmail;
	}

	public Users findByEmail(String email) {

		return userRepository.findByEmail(email);

	}

	@Override
	public UsersDTO registerUser(UsersDTO userRegistrationDTO) {
		Users user = new Users();
		user.setUsername(userRegistrationDTO.getUsername());
		user.setEmail(userRegistrationDTO.getEmail());
		user.setPassword(userRegistrationDTO.getPassword()); // Encrypt the password

		// You might want to set additional fields such as roles, enabled status, etc.

		List<Roles> getroles = rolle.findAll();
		Set<Roles> roles = new HashSet<>();
		getroles.forEach(r -> {
			if (r.getName().equals("ADMIN")) {
				roles.add(r);
			}
		});

		user.setRoles(roles);
		Users save = userRepository.save(user);
		UsersDTO u = new UsersDTO(user);
		return u;
	}

	@Override
	public Users registerUser(Users user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Users> findAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users updateUser(Users user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Users saveUser(Users user) {
		Users save = userRepository.save(user);
		return save;
	}

	// Implement other methods for managing users
}
