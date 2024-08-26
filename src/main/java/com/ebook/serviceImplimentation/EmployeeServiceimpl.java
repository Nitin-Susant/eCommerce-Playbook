package com.ebook.serviceImplimentation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebook.dto.EmployeeDTO;
import com.ebook.dto.UsersDTO;
import com.ebook.entity.Employees;
import com.ebook.entity.Roles;
import com.ebook.entity.Users;
import com.ebook.repositery.EmployeeRepository;
import com.ebook.repositery.RolesRepositrry;
import com.ebook.repositery.UserRepository;
import com.ebook.service.EmployeeService;
import com.ebook.service.UserService;
import com.ebook.utill.Conveters;

@Service
public class EmployeeServiceimpl implements EmployeeService {
	@Autowired
	UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

	@Autowired
	RolesRepositrry rolle;

	public Users findByUsername(String username) {
		// Implementation for finding an employee by username
		return null;
	}

	@Override
	public List<Users> findAllUsers() {

		return null;
	}

	@Autowired
	RolesRepositrry rolesRepositrry;

	@Autowired
	EmployeeRepository employeeRepository;
	
	
	 
	    public List<EmployeeDTO> getAllEmployees() {
	    	
	          List<Employees> all = employeeRepository.findAll();
	          List<EmployeeDTO> employees = new ArrayList<>();
	          all.forEach(e ->{
	        	  employees.add(Conveters.convert(e));
	          });
	          return employees;
	          
	    }

	public void registerEmployee(EmployeeDTO employeeDTO, String email) {
		// 1. Fetch User object based on email
		Users user = userRepository.findByEmail(email);

		// 2. Convert EmployeeDTO to Employee entity (assuming mapping)
		Employees employee = Conveters.convert(employeeDTO);

		// 3. Fetch Role object based on role name
		Roles role = rolesRepositrry.findByName(employeeDTO.getRole());

		// 4. Set associations (assuming unidirectional or bidirectional)
		Set<Roles> roles = user.getRoles() != null ? user.getRoles() : new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		user = userRepository.save(user);

		employee.setUser(user);

		// 5. Save Employee entity (assuming cascade or manual save for User)
		employeeRepository.save(employee); // Might need to save user first if unidirectional
	}

	@Override
	public void deleteUser(Long userId) {
		// Implementation for deleting an employee
	}

	@Override
	public UsersDTO registerUser(UsersDTO userRegistrationDTO) {
		Users user = new Users();
		user.setUsername(userRegistrationDTO.getUsername());
		user.setEmail(userRegistrationDTO.getEmail());
		user.setPassword(userRegistrationDTO.getPassword()); // Encrypt the password

		// You might want to set additional fields such as roles, enabled status, etc.

		Users save = userRepository.save(user);
		UsersDTO usersaed = new UsersDTO(user);
		return usersaed;
	}

	@Override
	public Users findByEmail(String email) {
		return userRepository.findByEmail(email);

	}

	@Override
	public Users registerUser(Users user) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users updateUser(Users user) {
		// TODO Auto-generated method stub
		return null;
	}
}
