package zerobase.table_reservation.exception.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	
	// JWT 발급 오류
	TOKEN_ISSUANCE_FAILD("TOKEN_ISSUANCE_FAILD", "JWT 생성 중 오류가 발생했습니다."),
	
	// 잘못된 접근 오류
	INVALID_ACCESS("INVALID_ACCESS", "잘못된 접근입니다."),
	
	// 잘못된 권한 접근 오류
	UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS", "이 리소스에 대한 접근 권한이 없습니다."),

	// 잘못된 인증 정보 오류
	INVALID_CREDENTIALS("INVALID_CREDENTIALS", "잘못된 사용자 이름 또는 비밀번호입니다."),
	
	// 잘못된 권한 정보 오류
	INVALID_USER_AUTHORIZATION("INVALID_USER_AUTHORIZATION", "잘못된 사용자 권한 정보입니다."),
	
	// 잘못된 예약 상태 코드 오류
	INVALID_RESERVATION_STATUS("INVALID_RESERVATION_STATUS", "잘못된 예약 상태 코드입니다."),

	// 사용자 없음 오류
	USER_NOT_FOUND("USER_NOT_FOUND", "주어진 사용자 이름의 사용자를 찾을 수 없습니다."),

	// 사용자 이미 존재 오류
	USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "이미 존재하는 사용자입니다."),

	// 매장 정보 없음 오류
	STORE_NOT_FOUND("STORE_NOT_FOUND", "매장 정보를 찾을 수 없습니다."),
	
	// 예약 정보 없음 오류
	RESERVATION_NOT_FOUND("RESERVATION_NOT_FOUND", "예약 정보를 찾을 수 없습니다."),
	
	// 잘못된 날짜 형식 오류
	INVALID_DATE_FORMAT("INVALID_DATE_FORMAT", "잘못된 날짜 형식입니다. 형식은 YYYYMMDD입니다.");

	private final String CODE;		// 오류 코드 (각 사용자에 대한 식별자)
	private final String MESSAGE;	// 오류 메세지 (사용자에게 표시될 메세지)

}
