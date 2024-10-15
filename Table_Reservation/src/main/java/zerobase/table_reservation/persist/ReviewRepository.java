package zerobase.table_reservation.persist;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.table_reservation.persist.entity.ReviewEntity;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
  
  /**
   * 특정 매장 ID에 대한 리뷰 목록을 조회하는 메소드
   * 
   * @param storeId 매장 ID
   * @return 해당 매장에 대한 리뷰 엔티티 리스트
   * 
   * 주어진 매장 ID에 해당하는 리뷰 엔티티를 조회하여 반환합니다.
   */
  List<ReviewEntity> findByReservationStoreId(Long storeId);
  
}
