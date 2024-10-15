package zerobase.table_reservation.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import zerobase.table_reservation.exception.TableReservationException;
import zerobase.table_reservation.exception.type.ErrorCode;

@Getter
@AllArgsConstructor
public enum Rating {
  
  VERY_BAD(1, "Very Bad"), // 매우 별로
  BAD(2, "Bad"), // 별로
  SOSO(3, "So-so"), // 평범
  GOOD(4, "Good"), // 좋음
  VERY_GOOD(5, "Very Good"); // 매우 좋음
  
  private final int score; // 숫자 값
  private final String description; // 사용자에게 보여질 텍스트
  
  public static Rating fromScore(int score) {
    for (Rating rating : values()) {
      if (rating.score == score) {
        return rating;
      }
    }
    throw new TableReservationException(ErrorCode.INVALID_SCORE);
  }
  
}
