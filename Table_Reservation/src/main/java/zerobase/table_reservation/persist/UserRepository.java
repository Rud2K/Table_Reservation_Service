package zerobase.table_reservation.persist;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zerobase.table_reservation.persist.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  /**
   * 사용자 이름으로 UserEntity를 조회하는 메소드
   * 
   * @param username 사용자 이름
   * @return Optional<UserEntity> 사용자가 존재하는 경우, 해당 UserEntity 객체를 감싼 Optional
   * 
   * 주어진 사용자 이름으로 데이터베이스에서 사용자 정보를 검색합니다.
   * 사용자가 존재하지 않을 경우 빈 Optional을 반환합니다.
   */
  Optional<UserEntity> findByUsername(String username);

  /**
   * 주어진 사용자 이름이 이미 존재하는지 확인하는 메소드
   * 
   * @param username 사용자 이름
   * @return boolean 사용자 이름이 존재하면 true, 존재하지 않으면 false
   * 
   * 주어진 사용자 이름이 데이터베이스에 존재하는지 확인하여 존재 여부에 따라 true 또는 false를 반환합니다.
   */
  boolean existsByUsername(String username);

}
