package zerobase.table_reservation.service.impl;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import zerobase.table_reservation.exception.TableReservationException;
import zerobase.table_reservation.exception.type.ErrorCode;
import zerobase.table_reservation.model.User;
import zerobase.table_reservation.model.constant.Role;
import zerobase.table_reservation.persist.UserRepository;
import zerobase.table_reservation.persist.entity.UserEntity;
import zerobase.table_reservation.security.TokenProvider;
import zerobase.table_reservation.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;

  @Transactional
  @Override
  public UserEntity signUp(User.SignUp request) {
    // 사용자 이름이 이미 존재하는지 확인
    if (this.userRepository.existsByUsername(request.getUsername())) {
      throw new TableReservationException(ErrorCode.USER_ALREADY_EXISTS);
    }

    // 사용자 정보 생성
    UserEntity user = UserEntity.builder()
        .username(request.getUsername())
        .password(this.passwordEncoder.encode(request.getPassword()))
        .role(Role.valueOf(request.getRole()))
        .ownedStores(new ArrayList<>())
        .reservations(new ArrayList<>())
        .build();

    // 사용자 정보 저장
    return this.userRepository.save(user);
  }

  @Override
  public String signIn(User.SignIn request) {
    // 사용자 이름으로 사용자 찾기
    UserEntity user = this.userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new TableReservationException(ErrorCode.USER_NOT_FOUND));

    // 비밀번호 확인
    if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new TableReservationException(ErrorCode.INVALID_CREDENTIALS);
    }

    try {
      // 인증 시도
      Authentication authentication = this.authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

      // 성공 시 JWT 생성
      return this.tokenProvider.createToken(authentication.getName(),
          authentication.getAuthorities());
    } catch (AuthenticationException e) {
      throw new TableReservationException(ErrorCode.INVALID_CREDENTIALS);
    }
  }

}
