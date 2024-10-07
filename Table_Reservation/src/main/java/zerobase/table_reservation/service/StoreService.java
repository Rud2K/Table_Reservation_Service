package zerobase.table_reservation.service;

import java.util.List;

import zerobase.table_reservation.model.Store;

public interface StoreService {
	
	/**
	 * 주어진 ID에 해당하는 매장을 조회하는 메소드
	 * 
	 * @param storeId 조회할 매장의 고유 식별자
	 * @return Store 매장 정보
	 * 
	 * 주어진 매장 ID를 기반으로 데이터베이스에서 매장 정보를 조회하여
	 * 해당 매장 정보를 반환합니다.
	 */
	Store getStoreById(Long storeId);
	
	/**
	 * 주어진 이름을 가진 매장 리스트를 조회하는 메소드
	 * 
	 * @param name 검색할 매장 이름
	 * @return 검색 조건에 맞는 매장 리스트
	 * 
	 * 주어진 매장 이름을 기준으로 데이터베이스에서 매장을 검색하여
	 * 이름에 해당 문자열이 포함된 모든 매장의 리스트를 반환합니다.
	 */
	List<Store> getStoresByName(String name);
	
	/**
	 * 새로운 매장을 생성하는 메소드
	 * 
	 * @param store 생성할 매장 정보
	 * @param username 매장을 생성하는 사용자 이름
	 * @return 생성된 매장 정보
	 * 
	 * 주어진 매장 정보를 기반으로 데이터베이스에 새로운 매장을 생성하고,
	 * 생성된 매장 정보를 반환합니다.
	 */
	Store createStore(Store store, String username);
	
	/**
	 * 주어진 ID의 매장 정보를 업데이트하는 메소드
	 * 
	 * @param storeId 업데이트할 매장의 고유 식별자
	 * @param store 수정된 매장 정보
	 * @param username 매장을 수정하는 사용자 이름
	 * @return 수정된 매장 정보
	 * 
	 * 주어진 매장 ID에 해당하는 매장 정보를 업데이트하고,
	 * 수정된 매장 정보를 반환합니다.
	 */
	Store updateStore(Long storeId, Store store, String username);
	
	/**
	 * 주어진 ID의 매장 정보를 삭제하는 메소드
	 * 
	 * @param storeId 삭제할 매장의 고유 식별자
	 * @param username 매장을 삭제하는 사용자 이름
	 * 
	 * 주어진 매장 ID에 해당하는 매장을 데이터베이스에서 삭제합니다.
	 */
	void deleteStore(Long storeId, String username);
	
}
