package zerobase.table_reservation.service;

import java.util.List;

import zerobase.table_reservation.model.Reservation;

public interface ReservationService {
	
	Reservation.Response getReservation(Long reservationId);
	
	Reservation.Response createReservation(Reservation.Request reservation, String username);
	
	Reservation.Response updateReservationStatus(Long reservationId, String status);
	
	Reservation.Response cancelReservation(Long reservationId);
	
	List<Reservation.Response> getUserReservations(Long userId);
	
	List<Reservation.Response> getReservationsForStore(Long storeId, String date);
	
}
