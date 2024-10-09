package zerobase.table_reservation.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Reservation {
	
	/**
	 * 예약 요청 정보를 담는 내부 정적 클래스
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Request {
		private Long storeId;	// 예약할 매장의 ID
	}
	
	/**
	 * 예약 응답 정보를 담는 내부 정적 클래스
	 */
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {
		private Long userId;					// 예약한 사용자 ID
		private Long storeId;					// 예약한 매장 ID
		private String storeName;				// 예약한 매장 이름
		private LocalDateTime reservationTime;	// 예약 시간
		private LocalDateTime cancellationTime;	// 취소 시간 (예약이 취소된 경우)
		private String status;
	}
	
}
