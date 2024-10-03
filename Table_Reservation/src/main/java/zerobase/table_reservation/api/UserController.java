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
	
	@PostMapping("/signup")
	public ResponseEntity<UserEntity> signUp(@RequestBody User.SignUp request) {
		UserEntity user = this.userService.signUp(request);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<String> signIn(@RequestBody User.SignIn request) {
		String token = this.userService.signIn(request);
		return ResponseEntity.ok(token);
	}
	
}
