package com.greenmart.app.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    // Use Spring's @Lazy here if needed:
    @Lazy
    private final UserDetailsService userDetailsService;
    
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    
    private final Long jwtExpiryMs = 86400000L;

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Objects> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .claim("roles", userDetails.getAuthorities())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpiryMs))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
     
    private Claims extractAllClaims(String token) {
         return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }
     
    public Boolean isTokenValid(String token, UserDetails userDetails) {
         final String username = extractUserName(token);
         return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
     
    private Key getSignInKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateRefresh(Map<String, Objects> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
    }
     
    private boolean isTokenExpired(String token) {
         return extractExpiration(token).before(new Date());
    }
     
    private Date extractExpiration(String token) {
         return extractClaim(token, Claims::getExpiration);
    }
     
    public String getEmailFromToken(String token) {
         return extractUserName(token);
    }
     
    public Boolean validateToken(String token) {
         String userEmail = extractUserName(token);
         if (StringUtils.isNotEmpty(userEmail) && !isTokenExpired(token)) {
             UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
             return isTokenValid(token, userDetails);
         }
         return false;
    }
     
    public String extractUserName(String token) {
         return extractClaim(token, Claims::getSubject);
    }
}
