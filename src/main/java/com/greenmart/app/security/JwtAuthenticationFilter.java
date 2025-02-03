package com.greenmart.app.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.greenmart.app.services.AuthenticationService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final AuthenticationService authenticationService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
		String token = extractToken(request);
		if(token != null) {
			UserDetails userDetails = authenticationService.validateToken(token);
			UsernamePasswordAuthenticationToken authentication = new  UsernamePasswordAuthenticationToken(
					userDetails,
					null,
					userDetails.getAuthorities()
			);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			if(userDetails instanceof AppUserDetails) {
				request.setAttribute("userId",((AppUserDetails) userDetails).getId());
			}
		}
		}catch(Exception ex) {
			//do not thow exception, just don't authenticate the user
			log.warn("Recieved invalid auth token");
		}
		
		filterChain.doFilter(request, response);
		
	}

	private String extractToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

}
