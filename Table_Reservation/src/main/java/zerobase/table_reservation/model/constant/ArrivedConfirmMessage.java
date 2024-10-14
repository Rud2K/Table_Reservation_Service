package zerobase.table_reservation.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ArrivedConfirmMessage {

  ARRIVAL_CONFIRMATION_SUCCESS("도착 확인이 완료되었습니다."),

  ARRIVAL_CONFIRMATION_FAILED("도착 확인에 실패하였습니다."),

  INVALID_REQUEST("잘못된 요청입니다.");

  private final String message;

}
