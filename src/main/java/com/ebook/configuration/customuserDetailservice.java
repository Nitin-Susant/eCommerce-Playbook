package com.ebook.configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ebook.entity.Roles;
import com.ebook.entity.Users;
import com.ebook.repositery.UserRepository;

 @Component

public class customuserDetailservice implements UserDetailsService {

	@Autowired
	UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users findByEmail = repository.findByEmail(email);
		System.out.println(findByEmail);
		Set<Roles> role = findByEmail.getRoles();
		Set<String> rolee = new HashSet<>();
	 
		String[] rolesArray = role.stream()
                .map(Roles::getName) // Assuming Roles has a field roleName for role name
                .toArray(String[]::new);
		
		 /*
		  *  Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        User user = userOptional.get();

        // Determine user type (replace with your approach)
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        if (user.getUserType() != null && user.getUserType().equals("EMPLOYEE")) { // Example using user type field
            authorities.add(new SimpleGrantedAuthority(ROLE_EMPLOYEE));
        } else if (user.getUserType() != null && user.getUserType().equals("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority(ROLE_ADMIN));
        } else {
            authorities.add(new SimpleGrantedAuthority(ROLE_USER)); // Default role (optional)
        }

        return new UserPrincipal(user, authorities); // Custom UserDetails implementation
   */
		
		
		UserDetails build = User.withUsername(findByEmail.getEmail()).password(findByEmail.getPassword())
				.roles(rolesArray).build();
		return build;
	}

}
