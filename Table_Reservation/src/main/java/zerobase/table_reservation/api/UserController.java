package zerobase.table_reservation.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import zerobase.table_reservation.model.User;
import zerobase.table_reservation.persist.entity.UserEntity;
import zerobase.table_reservation.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class UserController {
	
	private final UserService userService;
	
	/**
	 * 회원가입 요청을 처리하는 메소드
	 * 
	 * @param request 회원가입 요청 정보 (User.SignUp)
	 * @return 생성된 UserEntity 객체를 포함한 ResponseEntity
	 * 
	 * 클라이언트로부터 회원가입 정보를 받아
	 * UserService를 통해 처리한 후, 생성된 사용자 정보를 반환합니다.
	 */
	@PostMapping("/signup")
	public ResponseEntity<UserEntity> signUp(@RequestBody User.SignUp request) {
		UserEntity user = this.userService.signUp(request);
		return ResponseEntity.ok(user);
	}
	
	/**
	 * 로그인 요청을 처리하는 메소드
	 * 
	 * @param request 로그인 요청 정보 (User.SignIn)
	 * @return 로그인 성공 시 JWT 정보를 포함한 ResponseEntity
	 * 
	 * 클라이언트로부터 로그인 정보를 받아
	 * UserService를 통해 처리한 후, 생성된 JWT 정보를 반환합니다.
	 */
	@PostMapping("/signin")
	public ResponseEntity<String> signIn(@RequestBody User.SignIn request) {
		String token = this.userService.signIn(request);
		return ResponseEntity.ok(token);
	}
	
}
