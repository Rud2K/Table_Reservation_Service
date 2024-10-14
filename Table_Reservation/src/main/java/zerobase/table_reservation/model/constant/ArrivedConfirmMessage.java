package zerobase.table_reservation.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArrivedConfirmMessage {
  
  // 도착 확인이 성공적으로 완료되었을 경우
  ARRIVAL_CONFIRMATION_SUCCESS("도착 확인이 완료되었습니다."),
  
  // 도착 확인에 실패했을 경우
  ARRIVAL_CONFIRMATION_FAILED("도착 확인에 실패하였습니다."),
  
  // 잘못된 요청이 발생했을 경우
  INVALID_REQUEST("잘못된 요청입니다.");

  private final String message; // 각 열거형 값에 해당하는 메세지

}
