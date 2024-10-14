package zerobase.table_reservation.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import zerobase.table_reservation.exception.type.ErrorCode;
import zerobase.table_reservation.persist.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository; // 사용자 정보를 조회하기 위한 리포지토리

  /**
   * 주어진 사용자 이름으로 사용자 정보를 로드하는 메소드
   * 
   * @param username 사용자 이름
   * @return UserDetails 사용자 상세 정보
   * 
   * 사용자 이름에 해당하는 사용자를 데이터베이스에서 조회하고,
   * 사용자가 존재하지 않을 경우 UsernameNotFoundException을 발생시킵니다.
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.userRepository.findByUsername(username).orElseThrow(() -> {
      return new UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.toString());
    });
  }

}
