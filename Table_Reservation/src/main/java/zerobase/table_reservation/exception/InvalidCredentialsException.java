package zerobase.table_reservation.exception;

import lombok.Builder;
import lombok.Getter;
import zerobase.table_reservation.exception.type.ErrorCode;

@Getter
public class InvalidCredentialsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private ErrorCode errorCode;
	
	@Builder
	public InvalidCredentialsException(ErrorCode errorCode) {
		super(errorCode.getMESSAGE());
		this.errorCode = errorCode;
	}
}
