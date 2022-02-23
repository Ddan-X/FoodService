package com.cogent.fooddeliveryapp.security.jwt;

import java.nio.file.attribute.UserPrincipal;
import java.time.LocalDate;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cogent.fooddeliveryapp.security.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${com.cogent.fooddeliveryapp.jwtSecret}")
	private String jwtSecret;
	
	@Value("${com.cogent.fooddeliveryapp.jwtExpirationMs}")
	private long jwtExpirationMs;
	
	//to genertae the token
	public String generateToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl)authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(userPrincipal.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+jwtExpirationMs))//A JWT obtained after this timestamp should not be used.
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	//validation of token
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch ( ExpiredJwtException e) {//all are runtime exception
			logger.error("JWT token is expired :{}", e.getMessage());
			// TODO: handle exception
		}catch (UnsupportedJwtException e) {
			// TODO: handle exception
			logger.error("JWT token is unsupported:{}",e.getMessage());
		}catch (MalformedJwtException e) {
			// TODO: handle exception
			logger.error("Incalied JWT token :{}",e.getMessage());
		}catch (SignatureException e) {
			// TODO: handle exception
			logger.error("JWT signature is expired:{}",e.getMessage());
		}catch (IllegalArgumentException e) {
			// TODO: handle exception
			logger.error("JWT illegal or inappropriate argument:{}",e.getMessage());
		}
		return false;
	}
	
	//get name from the token ==> 
	public String getUserNameFromJwtToken(String authToken) {
		return Jwts.parser()// compact -->java object
				.setSigningKey(jwtSecret)// secret key->> ecoding is done
				.parseClaimsJws(authToken)//provided actual token
				.getBody()// extracting the body content 
				.getSubject(); //extracting the subject, when genertae the token, set name in subject, generateToken method
	}
	
}
