package com.ebook.configuration;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Configurations {

	@Bean
	UserDetailsService inMemoryAuth() {

		UserDetails user1 = User.withUsername("manash").password("123").roles("USER").build();
		UserDetails user2 = User.withUsername("ramesh").password("123").roles("ADMINE").build();
		UserDetails user3 = User.withUsername("susant").password("123").roles("USER").build();
		UserDetails user4 = User.withUsername("laxmi").password("123").roles("ADMINE").build();

		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(user1, user2, user3, user4);
		return manager;

	}

	@Bean
	PasswordEncoder passwordencoder() {

		return NoOpPasswordEncoder.getInstance();
	}
	  @Bean
    UserDetailsService detailsService() {
		
		return new customuserDetailservice();
	}
	
	@Bean
	AuthenticationProvider provider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordencoder());
//		provider.setUserDetailsService(inMemoryAuth());
		 provider.setUserDetailsService(detailsService());

		return provider;
	}

	
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		
//		http.csrf(Customizer.withDefaults())
//		.authorizeHttpRequests(auth ->{
//			auth.requestMatchers("/userlogin","/register","/style/**","/i/**").permitAll()
//			.anyRequest().authenticated();
//		})
//		 .formLogin(e ->{
//			 e.loginPage("/userlogin")
//			.loginProcessingUrl("/sucesslogin");
//		 }
//		 );
//		
//		return http.build();
//	}
//	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// advance security important
		http.csrf(Customizer.withDefaults()).authorizeHttpRequests(auth -> {
			auth.requestMatchers("/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
			.requestMatchers("/inventary/**").hasAnyAuthority("ROLE_EMPLOYEE","ROLE_ADMIN")
			.requestMatchers("/admin/**")
					.hasAuthority("ROLE_ADMIN")
					.requestMatchers("/public/**","/logins","/logout","/register", "/style/**", "/image/**").permitAll()
					.anyRequest().authenticated();
		}).formLogin(e -> {
			 e.loginPage("/logins");
			e.loginProcessingUrl("/sucesslogin");
			 
			e.successHandler((request, response, authentication) -> {

				Set<String> authoritys = new HashSet<>();

				Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
				for (GrantedAuthority grantedAuthority : authorities) {
					System.out.println(grantedAuthority.getAuthority());
					authoritys.add(grantedAuthority.getAuthority());
				}

				if (authoritys.contains("ROLE_ADMIN")) {
					response.sendRedirect("/admin/books/getallBooks");
				}
				else if (authoritys.contains("ROLE_EMPLOYEE")) {
					response.sendRedirect("/employee/getallBooks");
				}else {
					response.sendRedirect("public/books");
				}

			});
		})
		.logout(e ->{
			e.logoutUrl("/logout")
			 .logoutSuccessUrl("/logins")
            .permitAll();
		})
		;

		return http.build();
	}

}
