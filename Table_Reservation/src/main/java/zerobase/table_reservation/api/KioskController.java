package zerobase.table_reservation.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import zerobase.table_reservation.model.constant.ArrivedConfirmMessage;
import zerobase.table_reservation.service.KioskService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/arrived")
public class KioskController {

  private final KioskService kioskService;

  /**
   * 예약 도착 확인 메소드
   * POST /arrived/confirm
   * 
   * @param kioskId 확인할 키오스크 ID
   * @param reservationId 도착 확인할 예약 ID
   * @return 도착 확인 성공 또는 실패 메세지를 포함한 ResponseEntity
   * 
   * 주어진 키오스크 ID와 예약 ID를 사용하여 도착 확인을 수행합니다.
   * 도착 확인이 성공하면 성공 메세지를, 실패하면 실패 메세지를 반환합니다.
   */
  @PostMapping("/confirm")
  @PreAuthorize("hasRole('PARTNER') or hasRole('USER')")
  public ResponseEntity<String> confirmArrived(
      @RequestParam(name = "kioskId") Long kioskId,
      @RequestParam(name = "reservationId") Long reservationId) {
    if (this.kioskService.confirmArrived(kioskId, reservationId)) {
      return ResponseEntity.ok(ArrivedConfirmMessage.ARRIVAL_CONFIRMATION_SUCCESS.getMessage());
    } else {
      return ResponseEntity.badRequest().body(ArrivedConfirmMessage.ARRIVAL_CONFIRMATION_FAILED.getMessage());
    }
  }

}
