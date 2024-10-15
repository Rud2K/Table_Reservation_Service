package zerobase.table_reservation.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zerobase.table_reservation.exception.TableReservationException;
import zerobase.table_reservation.exception.type.ErrorCode;
import zerobase.table_reservation.model.Reservation;
import zerobase.table_reservation.model.constant.ReservationStatus;
import zerobase.table_reservation.persist.ReservationRepository;
import zerobase.table_reservation.persist.StoreRepository;
import zerobase.table_reservation.persist.UserRepository;
import zerobase.table_reservation.persist.entity.ReservationEntity;
import zerobase.table_reservation.persist.entity.StoreEntity;
import zerobase.table_reservation.persist.entity.UserEntity;
import zerobase.table_reservation.service.ReservationService;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository reservationRepository;
  private final StoreRepository storeRepository;
  private final UserRepository userRepository;

  @Override
  public Reservation.Response getReservation(Long reservationId) {
    ReservationEntity reservation = this.reservationRepository.findById(reservationId)
        .orElseThrow(() -> new TableReservationException(ErrorCode.RESERVATION_NOT_FOUND));

    return Reservation.Response.builder()
        .userId(reservation.getUser().getId())
        .storeId(reservation.getStore().getId()).storeName(reservation.getStore().getName())
        .reservationTime(reservation.getReservationTime())
        .cancellationTime(reservation.getCancellationTime())
        .status(reservation.getStatus().toString())
        .build();
  }

  @Override
  public Reservation.Response createReservation(Reservation.Request reservation, String username) {
    StoreEntity storeEntity = this.storeRepository.findById(reservation.getStoreId())
        .orElseThrow(() -> new TableReservationException(ErrorCode.STORE_NOT_FOUND));

    UserEntity userEntity = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new TableReservationException(ErrorCode.USER_NOT_FOUND));

    ReservationEntity reservationEntity = ReservationEntity.builder()
        .user(userEntity)
        .store(storeEntity)
        .reservationTime(LocalDateTime.now())
        .cancellationTime(null)
        .status(ReservationStatus.CONFIRMED)
        .build();

    this.reservationRepository.save(reservationEntity);

    return Reservation.Response.builder()
        .userId(userEntity.getId())
        .storeId(storeEntity.getId())
        .storeName(storeEntity.getName())
        .reservationTime(reservationEntity.getReservationTime())
        .cancellationTime(reservationEntity.getCancellationTime())
        .status(reservationEntity.getStatus().toString())
        .build();
  }

  @Override
  public Reservation.Response updateReservationStatus(Long reservationId, String status) {
    // 해당 메소드에서 예약 상태를 취소 상태로 업데이트할 수 없음
    if ("CANCELED".equalsIgnoreCase(status)) {
      throw new TableReservationException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    ReservationEntity reservationEntity = this.reservationRepository.findById(reservationId)
        .orElseThrow(() -> new TableReservationException(ErrorCode.RESERVATION_NOT_FOUND));

    ReservationStatus reservationStatus;
    try {
      reservationStatus = ReservationStatus.valueOf(status.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new TableReservationException(ErrorCode.INVALID_RESERVATION_STATUS);
    }

    reservationEntity.setStatus(reservationStatus);

    this.reservationRepository.save(reservationEntity);

    return Reservation.Response.builder()
        .userId(reservationEntity.getUser().getId())
        .storeId(reservationEntity.getStore().getId())
        .storeName(reservationEntity.getStore().getName())
        .reservationTime(reservationEntity.getReservationTime())
        .cancellationTime(reservationEntity.getCancellationTime())
        .status(reservationEntity.getStatus().toString())
        .build();
  }

  @Override
  public Reservation.Response cancelReservation(Long reservationId) {
    ReservationEntity reservationEntity = this.reservationRepository.findById(reservationId)
        .orElseThrow(() -> new TableReservationException(ErrorCode.RESERVATION_NOT_FOUND));

    // 예약 상태를 취소로 변경하고, 취소 시간을 현재 시간으로 설정
    reservationEntity.setStatus(ReservationStatus.CANCELED);
    reservationEntity.setCancellationTime(LocalDateTime.now());

    this.reservationRepository.save(reservationEntity);

    return Reservation.Response.builder()
        .userId(reservationEntity.getUser().getId())
        .storeId(reservationEntity.getStore().getId())
        .storeName(reservationEntity.getStore().getName())
        .reservationTime(reservationEntity.getReservationTime())
        .cancellationTime(reservationEntity.getCancellationTime())
        .status(reservationEntity.getStatus().toString())
        .build();
  }

  @Override
  public List<Reservation.Response> getUserReservations(Long userId) {
    List<ReservationEntity> reservationEntities = this.reservationRepository.findByUserId(userId);
    return reservationEntities.stream()
        .map(reservationEntity -> Reservation.Response.builder()
            .userId(reservationEntity.getUser().getId())
            .storeId(reservationEntity.getStore().getId())
            .storeName(reservationEntity.getStore().getName())
            .reservationTime(reservationEntity.getReservationTime())
            .cancellationTime(reservationEntity.getCancellationTime())
            .status(reservationEntity.getStatus().toString())
            .build())
        .collect(Collectors.toList());
  }

  @Override
  public List<Reservation.Response> getReservationsForStore(Long storeId, String date) {
    // 주어진 날짜 형식이 유효한지 검사
    LocalDate localDate;
    try {
      localDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
    } catch (DateTimeParseException e) {
      throw new TableReservationException(ErrorCode.INVALID_DATE_FORMAT);
    }

    // 해당 날짜의 시작과 끝 시간을 설정
    LocalDateTime startOfDay = localDate.atStartOfDay();
    LocalDateTime endOfDay = localDate.atTime(LocalTime.MAX);

    List<ReservationEntity> reservationEntities = this.reservationRepository
        .findByStoreIdAndReservationTimeBetween(storeId, startOfDay, endOfDay);


    return reservationEntities.stream()
        .map(reservationEntity -> Reservation.Response.builder()
            .userId(reservationEntity.getUser().getId())
            .storeId(reservationEntity.getStore().getId())
            .storeName(reservationEntity.getStore().getName())
            .reservationTime(reservationEntity.getReservationTime())
            .cancellationTime(reservationEntity.getCancellationTime())
            .status(reservationEntity.getStatus().toString())
            .build())
        .collect(Collectors.toList());
  }

}
