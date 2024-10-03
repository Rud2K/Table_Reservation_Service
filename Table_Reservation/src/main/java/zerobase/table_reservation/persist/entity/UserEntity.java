package zerobase.table_reservation.persist.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
}
