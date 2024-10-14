package zerobase.table_reservation.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

  private Long id; // 매장의 고유 ID
  private String name; // 매장 이름
  private String address; // 매장 주소
  private String number; // 매장 연락처
  private List<Reservation.Response> reservations; // 매장에 대한 예약 정보 리스트
  private Long ownerId; // 매장의 소유자 ID
  private String ownerUsername; // 매장의 소유자 사용자 이름

}
