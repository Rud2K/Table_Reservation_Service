package zerobase.table_reservation.service;

import zerobase.table_reservation.model.User;
import zerobase.table_reservation.persist.entity.UserEntity;

public interface UserService {

  /**
   * 사용자 회원가입을 처리하는 메소드
   * 
   * @param request 사용자 등록 요청 정보
   * @return 등록된 사용자 정보
   * 
   * 사용자 이름이 이미 존재하는지 확인하고,
   * 존재하지 않는 경우 새 사용자 정보를 생성하여 저장합니다.
   */
  UserEntity signUp(User.SignUp request);

  /**
   * 사용자 로그인을 처리하는 메소드
   * 
   * @param request 로그인 요청 정보
   * @return 생성된 JWT 정보
   * 
   * 사용자 이름과 비밀번호를 확인하고,
   * 인증이 성공할 경우 JWT를 생성하여 반환합니다.
   */
  String signIn(User.SignIn request);

}
