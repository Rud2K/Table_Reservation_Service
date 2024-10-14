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
import zerobase.table_reservation.exception.TableReservationException;
import zerobase.table_reservation.exception.type.ErrorCode;

@Component
public class TokenProvider {

  // JWT 클레임에서 역할 정보를 저장하는 키
  private static final String KEY_ROLES = "roles";

  // JWT 생성 시 사용할 비밀 키
  @Value("${spring.jwt.secret}")
  private String secretKey;

  // JWT 만료 시간
  @Value("${spring.jwt.expiration-time}")
  private long expirationTime;

  // HMAC 서명을 위한 키
  private Key key;

  /**
   * 비밀 키를 사용하여 HMAC 키를 초기화하는 메소드
   * 
   * @PostConstruct 어노테이션을 통해 Spring 컨테이너가 생성된 후 자동으로 호출됨
   */
  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(this.secretKey.getBytes());
  }

  /**
   * JWT를 생성하는 메소드
   * 
   * @param username 사용자 이름
   * @param authorities 사용자의 권한
   * @return 생성된 JWT
   * 
   * 사용자 이름과 권한을 기반으로 JWT를 생성하여 반환합니다.
   * 토큰의 유효기간은 설정된 만료 시간에 따라 결정됩니다.
   */
  public String createToken(String username, Collection<? extends GrantedAuthority> authorities) {
    // 클레임에 사용자 이름 설정
    Claims claims = Jwts.claims().setSubject(username);

    // 클레임에 권한 추가
    claims.put(KEY_ROLES,
        authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

    // 현재 시간과 만료 시간 설정
    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + this.expirationTime);

    try {
      // JWT 생성
      String token = Jwts.builder().setClaims(claims) // 클레임 설정
          .setIssuedAt(now) // 발급 시간 설정
          .setExpiration(expiredDate) // 만료 시간 설정
          .signWith(key, SignatureAlgorithm.HS512) // HMAC SHA-512 알고리즘으로 서명
          .compact(); // 토큰 압축

      return token;
    } catch (Exception e) {
      throw new TableReservationException(ErrorCode.TOKEN_ISSUANCE_FAILD);
    }
  }

  /**
   * JWT에서 사용자 이름을 추출하는 메소드
   * 
   * @param token JWT
   * @return 추출된 사용자 이름
   * 
   * JWT 토큰을 파싱하여 사용자 이름을 추출합니다.
   */
  public String getUsernameFromToken(String token) {
    try {
      // JWT를 파싱하여 사용자 이름 추출
      return Jwts.parserBuilder().setSigningKey(this.key) // 서명 키 설정
          .build().parseClaimsJws(token).getBody().getSubject(); // 클레임에서 사용자 이름 반환
    } catch (JwtException e) {
      throw new TableReservationException(ErrorCode.INVALID_CREDENTIALS);
    }
  }

  /**
   * JWT의 유효성을 검증하는 메소드
   * 
   * @param token JWT
   * @return 유효할 경우 true, 유효하지 않을 경우 false
   * 
   * JWT의 서명 및 만료 여부를 검증합니다.
   */
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
      return true;
    } catch (SecurityException | MalformedJwtException e) {
      throw new TableReservationException(ErrorCode.INVALID_CREDENTIALS); // 잘못된 서명
    } catch (ExpiredJwtException e) {
      throw new TableReservationException(ErrorCode.INVALID_CREDENTIALS); // 만료된 토큰
    } catch (UnsupportedJwtException e) {
      throw new TableReservationException(ErrorCode.INVALID_CREDENTIALS); // 지원되지 않는 토큰
    } catch (IllegalArgumentException e) {
      throw new TableReservationException(ErrorCode.INVALID_CREDENTIALS); // 잘못된 토큰
    }
  }

}
