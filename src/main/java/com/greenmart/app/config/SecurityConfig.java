package com.greenmart.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.greenmart.app.domain.entities.User;
import com.greenmart.app.repositories.UserRepository;
import com.greenmart.app.security.AppUserDetailsService;
import com.greenmart.app.security.JwtAuthenticationFilter;
import com.greenmart.app.services.AuthenticationService;

@Configuration
public class SecurityConfig {

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService) {
		return new JwtAuthenticationFilter(authenticationService);
	}
	
	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		return new AppUserDetailsService(userRepository);
//		AppUserDetailsService appUserDetailsService = new AppUserDetailsService(userRepository);
//
//        String email = "user@test.com";
//        userRepository.findByEmail(email).orElseGet(() -> {
//            User newUser = User.builder()
//                    .name("Test User")
//                    .email(email)
//                    .address("pune")
//                    .phoneNumber("9422587450")
//                    .password(passwordEncoder().encode("password123!"))
//                    .build();
//            return userRepository.save(newUser);
//        });
//
//        return appUserDetailsService;
    }
	
	


	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
		http
        .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/tags/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/tags/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/tags/**").permitAll()
                .anyRequest().authenticated()
        )
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;

		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}
}
