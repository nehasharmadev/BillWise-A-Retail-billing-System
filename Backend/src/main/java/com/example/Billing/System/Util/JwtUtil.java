package com.example.Billing.System.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;


@Component
public class JwtUtil {
	@Value("${jwt.secret_key}")
	private String SECRET_KEY;
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		  claims.put("roles", userDetails.getAuthorities().stream()
			        .map(GrantedAuthority::getAuthority)
			        .collect(Collectors.toList()));
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		// TODO Auto-generated method stub
		return Jwts.builder()
			       .setClaims(claims)
			       .setSubject(subject)
			       .setIssuedAt(new Date(System.currentTimeMillis()))
			       .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
			       .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
			       .compact();
		
	}
	public String generateRefreshToken(UserDetails userDetails) {
	    return Jwts.builder()
	               .setSubject(userDetails.getUsername())
	               .setIssuedAt(new Date(System.currentTimeMillis()))
	               .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day
	               .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
	               .compact();
	}
	
	public Boolean validateRefreshToken(String token) {
	    try {
	        extractAllClaims(token); // This will throw an exception if the token is invalid
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		// TODO Auto-generated method stub
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

		private Claims extractAllClaims(String token) {
			// TODO Auto-generated method stub
			return Jwts.parser()
					   .setSigningKey(SECRET_KEY)
					   .parseClaimsJws(token)
					   .getBody();
			
		}
	
	public Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
}
