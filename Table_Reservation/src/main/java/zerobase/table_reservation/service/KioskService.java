package zerobase.table_reservation.service;

public interface KioskService {

  boolean confirmArrived(Long kioskId, Long reservationId);

}
