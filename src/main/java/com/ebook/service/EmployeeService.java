package com.ebook.service;

import java.util.List;

import com.ebook.dto.EmployeeDTO;
import com.ebook.dto.UsersDTO;
import com.ebook.entity.Employees;
import com.ebook.entity.Users;

public interface EmployeeService {
  
	void registerEmployee(EmployeeDTO employeeDTO, String email);
    public Users findByEmail(String email);
    UsersDTO registerUser(UsersDTO userRegistrationDTO);
    // Other methods for managing users
    
    public List<EmployeeDTO> getAllEmployees();
    
    Users registerUser(Users user) throws Exception; // Define a generic registration method
    Users findByUsername(String username);
    List<Users> findAllUsers();
    Users updateUser(Users user);
    void deleteUser(Long userId);
    
    
    
}
