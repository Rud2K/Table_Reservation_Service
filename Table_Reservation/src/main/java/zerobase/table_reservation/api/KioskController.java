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
