package com.greenmart.app.services.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.greenmart.app.services.AuthenticationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	private final AuthenticationManager authenticationMananger;
	private final UserDetailsService userDetailsService;
	
	@Value("${jwt.secret}")
	private String sercretKey;
	
	private final Long jwtExpiryMs = 86400000L;

	@Override
	public UserDetails authenticate(String email, String password) {
		authenticationMananger.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		return userDetailsService.loadUserByUsername(email);
	}

	@Override
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", userDetails.getAuthorities());
		return Jwts.builder()
		.setClaims(claims)
		.setSubject(userDetails.getUsername())
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + jwtExpiryMs))
		.signWith(getSigningKey(),SignatureAlgorithm.HS256)
		.compact();
	}
	
	private Key getSigningKey() {
		byte[] keyBytes = sercretKey.getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public UserDetails validateToken(String token) {
		String username = extractUsername(token);
		return userDetailsService.loadUserByUsername(username);
	}
	
	private String extractUsername(String token) {
	Claims claims = Jwts.parserBuilder()
		.setSigningKey(getSigningKey())
		.build()
		.parseClaimsJws(token)
		.getBody();
	return claims.getSubject();
	}
}
