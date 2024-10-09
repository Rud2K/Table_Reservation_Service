package zerobase.table_reservation.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Reservation {
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Request {
		private Long storeId;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response {
		private Long userId;
		private Long storeId;
		private String storeName;
		private LocalDateTime reservationTime;
		private LocalDateTime cancellationTime;
		private String status;
	}
	
}
