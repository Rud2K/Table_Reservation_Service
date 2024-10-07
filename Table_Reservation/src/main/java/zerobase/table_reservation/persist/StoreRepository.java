package zerobase.table_reservation.persist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zerobase.table_reservation.persist.entity.StoreEntity;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
	
	/**
	 * 주어진 이름의 일부를 포함하는 매장 리스트를 대소문자 구분 없이 검색하는 메소드
	 * 
	 * @param name 검색할 매장 이름의 일부 (검색어)
	 * @return 검색 조건에 맞는 StoreEntity 리스트
	 * 
	 * 주어진 매장 이름을 기준으로 데이터베이스에서 부분 일치 검색을 수행합니다.
	 * 대소문자를 구분하지 않고 검색하며,
	 * 매장 이름에 해당 검색어가 포함된 모든 매장의 리스트를 반환합니다.
	 */
	List<StoreEntity> findByNameContainingIgnoreCase(String name);
	
}
