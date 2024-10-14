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
    StoreEntity store = this.storeRepository.findById(kioskId).orElse(null);
    ReservationEntity reservation = this.reservationRepository.findById(reservationId).orElse(null);

    if (store == null || reservation == null) {
      return false;
    }

    if (!reservation.getStore().getId().equals(store.getId())) {
      return false;
    }

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime reservationTime = reservation.getReservationTime();
    
    if (now.isBefore(reservationTime.minusMinutes(10))) {
      return false;
    }

    reservation.setStatus(ReservationStatus.ARRIVED);
    this.reservationRepository.save(reservation);

    return true;
  }

}
