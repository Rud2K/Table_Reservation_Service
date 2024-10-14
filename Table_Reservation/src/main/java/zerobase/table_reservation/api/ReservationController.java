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

  /**
   * 특정 예약 ID에 대한 예약 정보를 조회하는 메소드 GET /reservation/{reservationId}
   * 
   * @param reservationId 예약 ID
   * @return 조회한 예약 정보를 포함한 ResponseEntity
   * 
   * 주어진 예약 ID로 예약 정보를 조회하여 반환합니다.
   */
  @GetMapping("/{reservationId}")
  @PreAuthorize("hasRole('PARTNER') or hasRole('USER')")
  public ResponseEntity<Reservation.Response> getReservation(
      @PathVariable("reservationId") Long reservationId) {
    Reservation.Response reservation = this.reservationService.getReservation(reservationId);
    return ResponseEntity.ok(reservation);
  }

  /**
   * 새로운 예약을 생성하는 메소드 POST /reservation
   * 
   * @param reservation 생성할 예약 정보
   * @return 생성된 예약 정보를 포함한 ResponseEntity
   * 
   * 주어진 예약 정보를 받아 새로운 예약을 생성하고, 생성된 예약 정보를 반환합니다.
   */
  @PostMapping
  @PreAuthorize("hasRole('PARTNER') or hasRole('USER')")
  public ResponseEntity<Reservation.Response> createReservation(
      @RequestBody Reservation.Request reservation) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Reservation.Response newReservation =
        this.reservationService.createReservation(reservation, authentication.getName());
    return ResponseEntity.status(HttpStatus.CREATED).body(newReservation);
  }

  /**
   * 예약 상태를 업데이트하는 메소드 PUT /reservation/{reservationId}/{status}
   * 
   * @param reservationId 업데이트할 예약 ID
   * @param status 업데이트할 예약 상태
   * @return 업데이트된 예약 정보를 포함한 ResponseEntity
   * 
   * 주어진 예약 ID와 예약 상태를 받아 해당 예약의 상태를 수정하고, 수정된 정보를 반환합니다.
   */
  @PutMapping("/{reservationId}/{status}")
  @PreAuthorize("hasRole('PARTNER')")
  public ResponseEntity<Reservation.Response> updateReservationStatus(
      @PathVariable("reservationId") Long reservationId,
      @PathVariable("status") String status) {
    Reservation.Response updatedReservation =
        this.reservationService.updateReservationStatus(reservationId, status);
    return ResponseEntity.ok(updatedReservation);
  }

  /**
   * 특정 예약을 취소하는 메소드 PUT /reservation/{reservationId}/cancel
   * 
   * @param reservationId 취소할 예약 ID
   * @return 취소된 예약 정보를 포함한 ResponseEntity
   * 
   * 주어진 예약 ID로 예약을 취소하고, 취소된 정보를 반환합니다.
   */
  @PutMapping("/{reservationId}/cancel")
  @PreAuthorize("hasRole('PARTNER') or hasRole('USER')")
  public ResponseEntity<Reservation.Response> cancelReservation(
      @PathVariable("reservationId") Long reservationId) {
    Reservation.Response canceledReservation =
        this.reservationService.cancelReservation(reservationId);
    return ResponseEntity.ok(canceledReservation);
  }

  /**
   * 특정 사용자 ID에 대한 모든 예약 정보를 조회하는 메소드 GET /reservation/user/{userId}
   * 
   * @param userId 사용자 ID
   * @return 사용자 예약 리스트를 포함한 ResponseEntity
   * 
   * 주어진 사용자 ID로 모든 예약 정보를 조회하여 반환합니다.
   */
  @GetMapping("/user/{userId}")
  @PreAuthorize("hasRole('PARTNER')")
  public ResponseEntity<List<Reservation.Response>> getUserReservations(
      @PathVariable("userId") Long userId) {
    List<Reservation.Response> userReservations =
        this.reservationService.getUserReservations(userId);
    return ResponseEntity.ok(userReservations);
  }

  /**
   * 특정 매장 ID와 날짜에 대한 모든 예약 정보를 조회하는 메소드 GET /reservation/store/{storeId}/{date}
   * 
   * @param storeId 매장 ID
   * @param date 예약 날짜
   * @return 매장 예약 리스트를 포함한 ResponseEntity
   * 
   * 주어진 매장 ID와 날짜로 모든 예약 정보를 조회하여 반환합니다.
   */
  @GetMapping("/store/{storeId}/{date}")
  @PreAuthorize("hasRole('PARTNER')")
  public ResponseEntity<List<Reservation.Response>> getReservationsForStore(
      @PathVariable("storeId") Long storeId,
      @PathVariable("date") String date) {
    List<Reservation.Response> storeReservations =
        this.reservationService.getReservationsForStore(storeId, date);
    return ResponseEntity.ok(storeReservations);
  }

}
