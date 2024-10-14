package zerobase.table_reservation.service;

import java.util.List;

import zerobase.table_reservation.model.Reservation;

public interface ReservationService {

  /**
   * 주어진 예약 ID에 해당하는 예약 정보를 조회하는 메소드
   * 
   * @param reservationId 예약 ID
   * @return 예약 정보
   * 
   * 주어진 예약 ID를 기반으로 예약 정보를 조회하여,
   * 해당 예약의 상세 정보를 반환합니다.
   */
  Reservation.Response getReservation(Long reservationId);

  /**
   * 새로운 예약을 생성하는 메소드
   * 
   * @param reservation 생성할 예약 정보
   * @param username 예약자 이름
   * @return 생성된 예약 정보
   * 
   * 클라이언트로부터 받은 예약 정보를 기반으로 새로운 예약을 생성하고,
   * 생성된 예약의 상세 정보를 반환합니다.
   */
  Reservation.Response createReservation(Reservation.Request reservation, String username);

  /**
   * 예약의 상태를 업데이트하는 메소드
   * 
   * @param reservationId 업데이트할 예약 ID
   * @param status 변경할 예약 상태
   * @return 업데이트된 예약 정보
   * 
   * 주어진 예약 ID와 변경할 예약 상태를 기반으로 예약의 상태를 업데이트하고,
   * 업데이트된 예약의 상세 정보를 반환합니다.
   */
  Reservation.Response updateReservationStatus(Long reservationId, String status);

  /**
   * 주어진 예약 ID에 해당하는 예약을 취소하는 메소드
   * 
   * @param reservationId 취소할 예약 ID
   * @return 취소된 예약의 상세 정보
   * 
   * 주어진 예약 ID를 기반으로 예약을 취소하고,
   * 취소된 예약의 상세 정보를 반환합니다.
   */
  Reservation.Response cancelReservation(Long reservationId);

  /**
   * 주어진 사용자 ID에 대한 모든 예약을 조회하는 메소드
   * 
   * @param userId 사용자 ID
   * @return 해당 사용자가 만든 모든 예약 리스트
   * 
   * 주어진 사용자 ID를 기반으로 해당 사용자가 만든 모든 예약 목록을 반환합니다.
   */
  List<Reservation.Response> getUserReservations(Long userId);

  /**
   * 주어진 매장 ID와 날짜에 대한 모든 예약을 조회하는 메소드
   * 
   * @param storeId 매장 ID
   * @param date 조회할 날짜
   * @return 해당 매장에 대한 예약 리스트
   * 
   * 주어진 매장 ID와 날짜를 기반으로 해당 매장에 대한 예약 목록을 반환합니다.
   */
  List<Reservation.Response> getReservationsForStore(Long storeId, String date);

}
