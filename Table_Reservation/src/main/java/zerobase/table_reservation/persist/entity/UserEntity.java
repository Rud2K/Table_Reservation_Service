package zerobase.table_reservation.persist.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.table_reservation.model.constant.Role;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "users")
public class UserEntity implements UserDetails {

  // 고유 식별자 (자동 생성)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 사용자 이름
  private String username;

  // 사용자 비밀번호
  private String password;

  // 사용자 역할 (ROLE_USER 또는 ROLE_PARTNER)
  @Enumerated(EnumType.STRING)
  private Role role;

  // 사용자가 소유한 매장 정보 리스트
  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<StoreEntity> ownedStores;

  // 사용자가 예약한 예약 정보 리스트
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<ReservationEntity> reservations;

  /**
   * 사용자의 권한을 반환하는 메소드
   * 
   * @return GrantedAuthority의 컬렉션 (사용자의 역할을 기반으로 생성)
   * 
   * 이 메소드는 사용자의 역할을 기반으로 단일 권한을 반환합니다.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(this.role.name()));
  }

  /**
   * 매장을 사용자의 소유 매장 목록에 추가하는 메소드
   * 
   * @param store 추가할 StoreEntity 객체
   * 
   * 사용자가 매장을 소유하도록 매장을 추가하고,
   * 매장의 소유자 필드를 해당 사용자로 설정합니다.
   */
  public void addStore(StoreEntity store) {
    if (this.ownedStores == null) {
      this.ownedStores = new ArrayList<>();
    }
    this.ownedStores.add(store);
    store.setOwner(this);
  }

  /**
   * 매장을 사용자의 소유 매장 목록에서 제거하는 메소드
   * 
   * @param store 제거할 StoreEntity 객체
   * 
   * 사용자의 소유 매장 목록에서 매장을 제거하고,
   * 매장의 소유자 필드를 null로 설정하여 매장과의 연결을 해제합니다.
   */
  public void removeStore(StoreEntity store) {
    this.ownedStores.remove(store);
    store.setOwner(null);
  }

  /**
   * 예약을 사용자의 예약 목록에 추가하는 메소드
   * 
   * @param reservation 추가할 ReservationEntity 객체
   * 
   * 사용자의 예약 목록에 예약을 추가하고,
   * 해당 예약의 사용자 필드를 이 사용자로 설정합니다.
   */
  public void addReservation(ReservationEntity reservation) {
    this.reservations.add(reservation);
    reservation.setUser(this);
  }

  /**
   * 예약을 사용자의 예약 목록에서 제거하는 메소드
   * 
   * @param reservation 제거할 ReservationEntity 객체
   * 
   * 사용자의 예약 목록에서 해당 예약을 제거하고,
   * 해당 예약의 사용자 필드를 null로 설정하여 연결을 해제합니다.
   */
  public void removeReservation(ReservationEntity reservation) {
    this.reservations.remove(reservation);
    reservation.setUser(null);
  }

}
