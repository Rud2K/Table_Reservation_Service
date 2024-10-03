package zerobase.table_reservation.model;

import lombok.Data;

public class User {
	
	/**
	 * 사용자 회원가입 정보를 담는 내부 정적 클래스
	 */
	@Data
	public static class SignUp {
		private String username;	// 사용자 이름
		private String password;	// 비밀번호
		private String role;		// 사용자 역할 (ROLE_USER 또는 ROLE_PARTNER)
	}
	
	/**
	 * 사용자 로그인 정보를 담는 내부 정적 클래스
	 */
	@Data
	public static class SignIn {
		private String username;	// 사용자 이름
		private String password;	// 비밀번호
	}
	
}
