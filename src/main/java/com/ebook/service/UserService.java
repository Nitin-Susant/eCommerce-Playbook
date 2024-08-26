package com.ebook.service;

import java.util.List;

import com.ebook.dto.UsersDTO;
import com.ebook.entity.Address;
import com.ebook.entity.Order;
import com.ebook.entity.Users;

public interface UserService {
  
	UsersDTO registerUser(UsersDTO userRegistrationDTO);
    public Users findByEmail(String email);
    public Long findIdByEmail(String email);
    // Other methods for managing users
    public Users saveUser(Users user );
    
    public Address getAddress(Users u,Long id) ;
    Users registerUser(Users user) throws Exception; // Define a generic registration method
    Users findByUsername(String username);
    List<Users> findAllUsers();
    Users updateUser(Users user);
    void deleteUser(Long userId);
    
    public Address saveAddress(Address address,String email);
    
    public Order saveOrder(String email,Long id);
    
    public Order getOders(Users u, Long id);
    public boolean paymentsucess(Users u, Long id);
    public List<Order> getAllOders(Users u);
    
}
