package zerobase.table_reservation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zerobase.table_reservation.model.constant.Rating;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
  
  private Long reservationId; // 예약 ID
  private Long userId; // 사용자 ID
  private Long storeId; // 매장 ID
  private String content; // 리뷰 내용
  private Rating rating; // 평점
  
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateRequest {
    
    private Long userId; // 사용자 ID
    private String content; // 리뷰 내용
    private Rating rating; // 평점
    
  }
  
}
