package zerobase.table_reservation.security;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import zerobase.table_reservation.exception.InvalidCredentialsException;
import zerobase.table_reservation.exception.type.ErrorCode;

@Component
public class TokenProvider {
	
	private static final String KEY_ROLES = "roles";
	
	@Value("${spring.jwt.secret}")
	private String secretKey;
	
	@Value("${spring.jwt.expiration-time}")
	private long expirationTime;
	
	private Key key;
	
	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(this.secretKey.getBytes());
	}
	
	public String createToken(String username, Collection<? extends GrantedAuthority> authorities) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put(KEY_ROLES, authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList()));
		
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + this.expirationTime);
		
		try {
			String token = Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(now)
					.setExpiration(expiredDate)
					.signWith(key, SignatureAlgorithm.HS512)
					.compact();
			
			return token;
		} catch (Exception e) {
			throw new InvalidCredentialsException(ErrorCode.TOKEN_ISSUANCE_FAILD);
		}
	}
	
	public String getUsernameFromToken(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(this.key)
					.build()
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
		} catch (JwtException e) {
			throw new InvalidCredentialsException(ErrorCode.INVALID_CREDENTIALS);
		}
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			throw new InvalidCredentialsException(ErrorCode.INVALID_CREDENTIALS);	// 잘못된 서명
		} catch (ExpiredJwtException e) {
			throw new InvalidCredentialsException(ErrorCode.INVALID_CREDENTIALS);	// 만료된 토큰
		} catch (UnsupportedJwtException e) {
			throw new InvalidCredentialsException(ErrorCode.INVALID_CREDENTIALS);	// 지원되지 않는 토큰
		} catch (IllegalArgumentException e) {
			throw new InvalidCredentialsException(ErrorCode.INVALID_CREDENTIALS);	// 잘못된 토큰
		}
	}
	
}
