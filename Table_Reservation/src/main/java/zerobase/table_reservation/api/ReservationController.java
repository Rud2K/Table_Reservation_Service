package zerobase.table_reservation.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zerobase.table_reservation.model.Reservation;
import zerobase.table_reservation.service.ReservationService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/reservation")
public class ReservationController {
	
	private final ReservationService reservationService;
	
	// 예약 조회
	@GetMapping("/{reservationId}")
	@PreAuthorize("hasRole('PARTNER') or hasRole('USER')")
	public ResponseEntity<Reservation.Response> getReservation(@PathVariable("reservationId") Long reservationId) {
		Reservation.Response reservation = this.reservationService.getReservation(reservationId);
		return ResponseEntity.ok(reservation);
	}
	
	// 예약 생성
	@PostMapping
	@PreAuthorize("hasRole('PARTNER') or hasRole('USER')")
	public ResponseEntity<Reservation.Response> createReservation(@RequestBody Reservation.Request reservation) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Reservation.Response newReservation = this.reservationService.createReservation(reservation, authentication.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(newReservation);
	}
	
	// 예약 상태 업데이트 (PARTNER)
	@PutMapping("/{reservationId}/{status}")
	@PreAuthorize("hasRole('PARTNER')")
	public ResponseEntity<Reservation.Response> updateReservationStatus(
			@PathVariable("reservationId") Long reservationId,
			@PathVariable("status") String status) {
		Reservation.Response updatedReservation = this.reservationService.updateReservationStatus(reservationId, status);
		return ResponseEntity.ok(updatedReservation);
	}
	
	// 예약 취소
	@PutMapping("/{reservationId}/cancel")
	@PreAuthorize("hasRole('PARTNER') or hasRole('USER')")
	public ResponseEntity<Reservation.Response> cancelReservation(@PathVariable("reservationId") Long reservationId) {
		Reservation.Response canceledReservation = this.reservationService.cancelReservation(reservationId);
		return ResponseEntity.ok(canceledReservation);
	}
	
	// 특정 사용자의 예약 정보 조회 (PARTNER)
	@GetMapping("/user/{userId}")
	@PreAuthorize("hasRole('PARTNER')")
	public ResponseEntity<List<Reservation.Response>> getUserReservations(@PathVariable("userId") Long userId) {
		List<Reservation.Response> userReservations = this.reservationService.getUserReservations(userId);
		return ResponseEntity.ok(userReservations);
	}
	
	// 특정 매장의 특정 날짜 예약 목록 조회
	@GetMapping("/store/{storeId}/{date}")
	@PreAuthorize("hasRole('PARTNER')")
	public ResponseEntity<List<Reservation.Response>> getReservationsForStore(
			@PathVariable("storeId") Long storeId,
			@PathVariable("date") String date) {
		List<Reservation.Response> storeReservations = this.reservationService.getReservationsForStore(storeId, date);
		return ResponseEntity.ok(storeReservations);
	}
	
}
