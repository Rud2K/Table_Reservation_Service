package zerobase.table_reservation.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import zerobase.table_reservation.service.CustomUserDetailsService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	// JWT를 처리하는 컴포넌트
	private final TokenProvider tokenProvider;
	
	// 사용자 세부 정보를 로드하는 서비스
	private final CustomUserDetailsService customUserDetailsService;
	
	/**
	 * 요청 필터링을 수행하는 메소드
	 * 
	 * @param request HTTP 요청
	 * @param response HTTP 응답
	 * @param filterChain 필터 체인
	 * 
	 * JWT를 검증하고, 해당 사용자의 인증 정보를 SecurityContext에 설정합니다.
	 * 요청이 필터를 통과한 후 다음 필터로 전달됩니다.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 요청에서 JWT를 추출
		String token = this.getTokenFromRequest(request);
		
		// 토큰이 존재하고 유효한 경우
		if (StringUtils.hasText(token)) {
			if (this.tokenProvider.validateToken(token)) {
				
				// 토큰에서 사용자 이름 추출
				String username = this.tokenProvider.getUsernameFromToken(token);
				
				// 사용자 세부 정보 로드
				UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
				
				// 인증 객체 생성
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				
				// 요청의 세부정보 설정
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				// SecurityContext에 인증 정보 설정
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		
		// 다음 필터로 요청 및 응답 정보 전달
		filterChain.doFilter(request, response);
	}
	
	/**
	 * HTTP 요청에서 JWT를 추출하는 메소드
	 * 
	 * @param request HTTP 요청
	 * @return 추출된 JWT 또는 null
	 * 
	 * 요청 헤더에서 Authorization 정보를 가져와 Bearer 토큰을 확인한 후,
	 * 유효한 경우 토큰을 반환합니다.
	 */
	private String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		
		// Bearer로 시작하는 경우, Bearer 부분을 제거하고 토큰 반환
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
}
