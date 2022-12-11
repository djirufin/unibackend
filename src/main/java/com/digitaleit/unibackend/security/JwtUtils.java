package com.digitaleit.unibackend.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${dis.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${dis.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	public String generateJwtToken(Authentication authentication) {
		
		CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();
		
		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
		        .setIssuedAt(new Date())
		        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
		        .signWith(SignatureAlgorithm.HS512, jwtSecret)
		        .compact();
	}
	
	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch(SignatureException | MalformedJwtException e) {
			logger.error("Invalid Jwt token {}", e.getMessage());
		}catch(ExpiredJwtException e) {
			logger.error("Jwt token is expired {}", e.getMessage());
		}catch(UnsupportedJwtException e) {
			logger.error("Jwt token is not supported {}", e.getMessage());
		}catch(IllegalArgumentException e) {
			logger.error("Jwt claims string is empty {}", e.getMessage());
		}
		return false;
	}
	
}
