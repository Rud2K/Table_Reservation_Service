package zerobase.table_reservation.exception;

import lombok.Builder;
import lombok.Getter;
import zerobase.table_reservation.exception.type.ErrorCode;

@Getter
public class UserException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private ErrorCode errorCode;	// 발생한 오류의 코드와 메세지를 포함하는 ErrorCode 객체
	
	/**
	 * InvalidCredentialsException 생성자
	 * 
	 * @param errorCode 발생한 오류의 코드와 메세지를 포함하는 ErrorCode 객체
	 * 
	 * 이 예외는 잘못된 인증 정보로 인해 발생하며,
	 * 주어진 오류 코드에 맞는 메세지가 상위 클래스인 RuntimeException에 전달됩니다.
	 */
	@Builder
	public UserException(ErrorCode errorCode) {
		super(errorCode.getMESSAGE());
		this.errorCode = errorCode;
	}
}
