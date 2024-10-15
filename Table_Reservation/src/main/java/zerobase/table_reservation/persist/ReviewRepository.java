package zerobase.table_reservation.persist;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.table_reservation.persist.entity.ReviewEntity;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
  
  List<ReviewEntity> findByReservationStoreId(Long storeId);
  
}
