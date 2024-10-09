package zerobase.table_reservation.persist;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zerobase.table_reservation.persist.entity.ReservationEntity;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
	
	/**
	 * 주어진 사용자 ID에 대한 모든 예약 엔티티를 조회하는 메소드
	 * 
	 * @param userId 사용자의 ID
	 * @return 주어진 사용자 ID에 해당하는 ReservationEntity 리스트
	 * 
	 * 주어진 사용자 ID를 기준으로 데이터베이스에서 예약 정보를 조회하여,
	 * 해당 사용자가 만든 모든 예약 목록을 반환합니다.
	 */
	List<ReservationEntity> findByUserId(Long userId);
	
	/**
	 * 주어진 매장 ID에 대한 모든 예약 엔티티를 조회하는 메소드
	 * 
	 * @param storeId 매장의 ID
	 * @return 주어진 매장 ID에 해당하는 ReservationEntity 리스트
	 * 
	 * 주어진 매장 ID를 기준으로 데이터베이스에서 예약 정보를 조회하여,
	 * 해당 매장에 대한 모든 예약 목록을 반환합니다.
	 */
	List<ReservationEntity> findByStoreId(Long storeId);
	
	/**
	 * 주어진 매장 ID와 예약 시간 범위에 해당하는 예약 엔티티를 조회하는 메소드
	 * 
	 * @param storeId 매장의 ID
	 * @param startDate 예약 시작 시간
	 * @param endDate 예약 종료 시간
	 * @return 주어진 조건에 해당하는 ReservationEntity 리스트
	 * 
	 * 주어진 매장 ID와 예약 시간 범위를 기준으로 데이터베이스에서 예약 정보를 조회하여,
	 * 해당 매장에 대한 특정 시간 범위의 예약 목록을 반환합니다.
	 */
	List<ReservationEntity> findByStoreIdAndReservationTimeBetween(Long storeId, LocalDateTime startDate, LocalDateTime endDate);
	
}
