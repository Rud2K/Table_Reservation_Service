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
  
  private Long reservationId;
  private Long userId;
  private Long storeId;
  private String content;
  private Rating rating;
  
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class UpdateRequest {
    
    private Long userId;
    private String content;
    private Rating rating;
    
  }
  
}
