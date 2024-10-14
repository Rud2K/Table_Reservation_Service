package zerobase.table_reservation.service.impl;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import zerobase.table_reservation.model.constant.ReservationStatus;
import zerobase.table_reservation.persist.ReservationRepository;
import zerobase.table_reservation.persist.StoreRepository;
import zerobase.table_reservation.persist.entity.ReservationEntity;
import zerobase.table_reservation.persist.entity.StoreEntity;
import zerobase.table_reservation.service.KioskService;

@Service
@RequiredArgsConstructor
public class KioskServiceImpl implements KioskService {

  private final StoreRepository storeRepository;
  private final ReservationRepository reservationRepository;

  @Override
  public boolean confirmArrived(Long kioskId, Long reservationId) {
    // 매장 정보 및 예약 정보 확인
    StoreEntity store = this.storeRepository.findById(kioskId).orElse(null);
    ReservationEntity reservation = this.reservationRepository.findById(reservationId).orElse(null);

    // 매장 및 예약 정보가 없을 시 false
    if (store == null || reservation == null) {
      return false;
    }

    // 예약이 해당 매장에 속해있지 않은 경우 false
    if (!reservation.getStore().getId().equals(store.getId())) {
      return false;
    }

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime reservationTime = reservation.getReservationTime();
    
    // 예약 시간이 현재 시간으로부터 10분 전 이후일 경우 도착 확인 거부
    if (now.isBefore(reservationTime.minusMinutes(10))) {
      return false;
    }

    // 도착 성공 시 예약 상태 업데이트
    reservation.setStatus(ReservationStatus.ARRIVED);
    this.reservationRepository.save(reservation);

    return true;
  }

}
