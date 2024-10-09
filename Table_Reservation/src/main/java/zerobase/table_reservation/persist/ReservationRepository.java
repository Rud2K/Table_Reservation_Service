package zerobase.table_reservation.persist;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zerobase.table_reservation.persist.entity.ReservationEntity;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
	
	List<ReservationEntity> findByUserId(Long userId);
	
	List<ReservationEntity> findByStoreId(Long storeId);
	
	List<ReservationEntity> findByStoreIdAndReservationTimeBetween(Long storeId, LocalDateTime startDate, LocalDateTime endDate);
	
}
