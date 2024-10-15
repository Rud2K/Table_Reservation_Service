package zerobase.table_reservation.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import zerobase.table_reservation.exception.TableReservationException;
import zerobase.table_reservation.exception.type.ErrorCode;
import zerobase.table_reservation.model.Review;
import zerobase.table_reservation.model.constant.ReservationStatus;
import zerobase.table_reservation.persist.ReservationRepository;
import zerobase.table_reservation.persist.ReviewRepository;
import zerobase.table_reservation.persist.StoreRepository;
import zerobase.table_reservation.persist.UserRepository;
import zerobase.table_reservation.persist.entity.ReservationEntity;
import zerobase.table_reservation.persist.entity.ReviewEntity;
import zerobase.table_reservation.persist.entity.StoreEntity;
import zerobase.table_reservation.persist.entity.UserEntity;
import zerobase.table_reservation.service.ReviewService;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
  
  private final ReviewRepository reviewRepository;
  private final ReservationRepository reservationRepository;
  private final UserRepository userRepository;
  private final StoreRepository storeRepository;
  
  @Override
  public Review createReview(Review review) {
    // 예약 확인
    ReservationEntity reservation = this.reservationRepository.findById(review.getReservationId())
        .orElseThrow(() -> new TableReservationException(ErrorCode.RESERVATION_NOT_FOUND));
    
    // 예약 상태 COMPLETED인지 확인
    if (!ReservationStatus.COMPLETED.toString().equalsIgnoreCase(reservation.getStatus().toString())) {
      throw new TableReservationException(ErrorCode.INVALID_RESERVATION_STATUS);
    }
    
    // 사용자 확인
    UserEntity user = this.userRepository.findById(review.getUserId())
        .orElseThrow(() -> new TableReservationException(ErrorCode.USER_NOT_FOUND));
    
    // 매장 정보 확인
    StoreEntity store = this.storeRepository.findById(review.getStoreId())
        .orElseThrow(() -> new TableReservationException(ErrorCode.STORE_NOT_FOUND));
    
    // 엔티티 생성
    ReviewEntity reviewEntity = ReviewEntity.builder()
        .reservation(reservation)
        .user(user)
        .store(store)
        .content(review.getContent())
        .rating(review.getRating())
        .createdAt(LocalDateTime.now())
        .updatedAt(null)
        .build();
    
    // DB에 저장
    this.reviewRepository.save(reviewEntity);
    
    return Review.builder()
        .reservationId(reservation.getId())
        .userId(user.getId())
        .storeId(store.getId())
        .content(reviewEntity.getContent())
        .rating(review.getRating())
        .build();
  }

  @Override
  public Review updateReview(Long reviewId, Review.UpdateRequest review) {
    // 리뷰 확인
    ReviewEntity reviewEntity = this.reviewRepository.findById(reviewId)
        .orElseThrow(() -> new TableReservationException(ErrorCode.REVIEW_NOT_FOUND));
    
    // 확인한 리뷰 정보의 사용자 정보와 요청을 보낸 사용자의 정보가 일치한지 확인
    if (!review.getUserId().equals(reviewEntity.getUser().getId())) {
      throw new TableReservationException(ErrorCode.UNAUTHORIZED_ACCESS);
    }
    
    // DB에 저장
    reviewEntity.setContent(review.getContent());
    reviewEntity.setRating(review.getRating());
    reviewEntity.setUpdatedAt(LocalDateTime.now());
    
    this.reviewRepository.save(reviewEntity);
    
    return Review.builder()
        .reservationId(reviewEntity.getReservation().getId())
        .userId(reviewEntity.getUser().getId())
        .storeId(reviewEntity.getStore().getId())
        .content(reviewEntity.getContent())
        .rating(reviewEntity.getRating())
        .build();
  }

  @Override
  public void deleteReview(Long reviewId, String username) {
    // 로직 오류 있음 추후 수정 요망
    ReviewEntity review = this.reviewRepository.findById(reviewId)
        .orElseThrow(() -> new TableReservationException(ErrorCode.REVIEW_NOT_FOUND));
    
    UserEntity user = this.userRepository.findByUsername(username)
        .orElseThrow(() -> new TableReservationException(ErrorCode.USER_NOT_FOUND));
    
    boolean isPartner = user.getAuthorities().stream()
        .anyMatch(authority -> authority.getAuthority().equals("ROLE_PARTNER"));
    
    if (!review.getUser().getId().equals(user.getId()) && !isPartner) {
      throw new TableReservationException(ErrorCode.UNAUTHORIZED_ACCESS);
    }
    
    this.reviewRepository.delete(review);
  }

  @Override
  public List<Review> getReviewsByStore(Long storeId) {
    return this.reviewRepository.findByReservationStoreId(storeId).stream()
        .map(reviewEntity -> Review.builder()
            .reservationId(reviewEntity.getReservation().getId())
            .userId(reviewEntity.getUser().getId())
            .storeId(reviewEntity.getReservation().getStore().getId())
            .content(reviewEntity.getContent())
            .rating(reviewEntity.getRating())
            .build())
        .collect(Collectors.toList());
  }
  
}
