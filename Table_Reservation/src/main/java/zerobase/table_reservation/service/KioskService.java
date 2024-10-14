package zerobase.table_reservation.service;

public interface KioskService {
  
  /**
   * 예약자 도착을 확인하는 메소드
   * 
   * @param kioskId 도착을 확인할 키오스크 ID (매장 ID)
   * @param reservationId 도착 확인할 예약 ID
   * @return 도착 확인 성공 여부 (성공 시 true, 실패 시 false)
   * 
   * 주어진 키오스크와 예약 정보를 조회하여 유효성을 검사한 후,
   * 예약 시간이 현재 시각으로부터 10분 전 이상일 경우 'ARRIVED' 상태로 업데이트합니다.
   */
  boolean confirmArrived(Long kioskId, Long reservationId);

}
