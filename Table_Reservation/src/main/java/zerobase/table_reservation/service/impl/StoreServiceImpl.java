package zerobase.table_reservation.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zerobase.table_reservation.exception.TableReservationException;
import zerobase.table_reservation.exception.type.ErrorCode;
import zerobase.table_reservation.model.Reservation;
import zerobase.table_reservation.model.Store;
import zerobase.table_reservation.persist.ReservationRepository;
import zerobase.table_reservation.persist.StoreRepository;
import zerobase.table_reservation.persist.UserRepository;
import zerobase.table_reservation.persist.entity.ReservationEntity;
import zerobase.table_reservation.persist.entity.StoreEntity;
import zerobase.table_reservation.persist.entity.UserEntity;
import zerobase.table_reservation.service.StoreService;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
	
	private final StoreRepository storeRepository;
	private final ReservationRepository reservationRepository;
	private final UserRepository userRepository;
	
	@Override
	public Store getStoreById(Long storeId) {
		StoreEntity storeEntity = this.storeRepository.findById(storeId)
				.orElseThrow(() -> new TableReservationException(ErrorCode.STORE_NOT_FOUND));
		
		// 해당 예약의 정보를 리스트로 변환
		List<Reservation.Response> reservations = storeEntity.getReservations().stream()
				.map(reservation -> Reservation.Response.builder()
						.username(reservation.getUser().getUsername())
						.storeName(storeEntity.getName())
						.reservationTime(reservation.getReservationTime())
						.status(reservation.getStatus().name())
						.build())
				.collect(Collectors.toList());
		
		return Store.builder()
				.id(storeEntity.getId())
				.name(storeEntity.getName())
				.address(storeEntity.getAddress())
				.number(storeEntity.getNumber())
				.reservations(reservations)
				.ownerId(storeEntity.getOwner().getId())
				.ownerUsername(storeEntity.getOwner().getUsername())
				.build();
	}
	
	@Override
	public List<Store> getStoresByName(String name) {
		List<Store> stores = new ArrayList<>();
		List<StoreEntity> storeEntities = this.storeRepository.findByNameContainingIgnoreCase(name);
		
		// StoreEntity 리스트를 Store 객체로 변환
		for (StoreEntity storeEntity : storeEntities) {
			List<Reservation.Response> reservationList = new ArrayList<>();
			
			// 매장의 예약 정보를 변환
			for (ReservationEntity reservation : storeEntity.getReservations()) {
				reservationList.add(Reservation.Response.builder()
						.username(reservation.getUser().getUsername())
						.storeName(storeEntity.getName())
						.reservationTime(reservation.getReservationTime())
						.status(reservation.getStatus().toString())
						.build());
			}
			
			Store store = Store.builder()
					.id(storeEntity.getId())
					.name(storeEntity.getName())
					.address(storeEntity.getAddress())
					.number(storeEntity.getNumber())
					.reservations(reservationList)
					.ownerId(storeEntity.getOwner().getId())
					.ownerUsername(storeEntity.getOwner().getUsername())
					.build();
			
			stores.add(store);
		}
		
		return stores;
	}
	
	@Override
	public Store createStore(Store store, String username) {
		UserEntity owner = this.userRepository.findByUsername(username)
				.orElseThrow(() -> new TableReservationException(ErrorCode.USER_NOT_FOUND));
		
		StoreEntity storeEntity = StoreEntity.builder()
				.id(store.getId())
				.name(store.getName())
				.address(store.getAddress())
				.number(store.getNumber())
				.reservations(new ArrayList<>())
				.owner(owner)
				.build();
		
		this.storeRepository.save(storeEntity);
		
		return Store.builder()
				.id(storeEntity.getId())
				.name(storeEntity.getName())
				.address(storeEntity.getAddress())
				.number(storeEntity.getNumber())
				.ownerId(owner.getId())
				.ownerUsername(owner.getUsername())
				.build();
	}
	
	@Override
	public Store updateStore(Long storeId, Store store, String username) {
		StoreEntity storeEntity = this.storeRepository.findById(storeId)
				.orElseThrow(() -> new TableReservationException(ErrorCode.STORE_NOT_FOUND));
		
		UserEntity owner = this.userRepository.findByUsername(username)
				.orElseThrow(() -> new TableReservationException(ErrorCode.USER_NOT_FOUND));
		
		// 매장의 소유자 확인
		if (!storeEntity.getOwner().getId().equals(owner.getId())) {
			throw new TableReservationException(ErrorCode.UNAUTHORIZED_ACCESS);
		}
		
		// 매장 정보 업데이트
		storeEntity.setName(store.getName());
		storeEntity.setAddress(store.getAddress());
		storeEntity.setNumber(store.getNumber());
		
		this.storeRepository.save(storeEntity);
				
		return Store.builder()
				.id(storeEntity.getId())
				.name(storeEntity.getName())
				.address(storeEntity.getAddress())
				.number(storeEntity.getNumber())
				.ownerId(owner.getId())
				.ownerUsername(owner.getUsername())
				.build();
	}
	
	@Override
	public void deleteStore(Long storeId, String username) {
		StoreEntity storeEntity = this.storeRepository.findById(storeId)
				.orElseThrow(() -> new TableReservationException(ErrorCode.STORE_NOT_FOUND));
		
		UserEntity userEntity = this.userRepository.findByUsername(username)
				.orElseThrow(() -> new TableReservationException(ErrorCode.USER_NOT_FOUND));
		
		// 매장의 소유자 확인
		if (!storeEntity.getOwner().getId().equals(userEntity.getId())) {
			throw new TableReservationException(ErrorCode.UNAUTHORIZED_ACCESS);
		}
		
		// 매장에 예약이 있을 경우 예약 삭제
		List<ReservationEntity> reservations = storeEntity.getReservations();
		if (!reservations.isEmpty()) {
			for (ReservationEntity reservation : reservations) {
				this.reservationRepository.delete(reservation);
			}
		}
		
		// 매장 소유자에게서 매장 제거
		userEntity.removeStore(storeEntity);
		
		// 매장 삭제
		this.storeRepository.delete(storeEntity);
	}
	
}
