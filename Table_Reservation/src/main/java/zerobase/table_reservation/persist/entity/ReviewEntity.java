package zerobase.table_reservation.persist.entity;

import java.time.LocalDateTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.table_reservation.model.constant.Rating;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "reviews")
public class ReviewEntity {
  
  // 고유 식별자 (자동 생성)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  // 해당 리뷰의 예약 정보
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "reservation_id", nullable = false)
  private ReservationEntity reservation;
  
  // 리뷰를 남긴 사용자 정보
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;
  
  // 리뷰를 남긴 매장 정보
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "store_id", nullable = false)
  private StoreEntity store;
  
  // 리뷰 내용
  @Column(nullable = false, length = 500)
  private String content;
  
  // 별점 (1 ~ 5)
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Rating rating;
  
  // 리뷰 작성 시간
  private LocalDateTime createdAt;
  
  // 리뷰 수정 시간
  private LocalDateTime updatedAt;
  
}
