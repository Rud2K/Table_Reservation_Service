package zerobase.table_reservation.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// 잘못된 인증 정보 오류
	INVALID_CREDENTIALS("INVALID_CREDENTIALS", "잘못된 사용자 이름 또는 비밀번호입니다."),

	// 사용자 없음 오류
	USER_NOT_FOUND("USER_NOT_FOUND", "주어진 사용자 이름의 사용자를 찾을 수 없습니다."),

	// 사용자 이미 존재 오류
	USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "이미 존재하는 사용자입니다."),
	
	// JWT 발급 오류
	TOKEN_ISSUANCE_FAILD("TOKEN_ISSUANCE_FAILD", "JWT 생성 중 오류가 발생했습니다."),

	// 매장 정보 없음 오류
	STORE_NOT_FOUND("STORE_NOT_FOUND", "매장 정보를 찾을 수 없습니다.");

	private final String CODE; // 오류 코드
	private final String MESSAGE; // 오류 메세지

}
