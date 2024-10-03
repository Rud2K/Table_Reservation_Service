package zerobase.table_reservation.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import zerobase.table_reservation.exception.InvalidCredentialsException;
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
		if (this.userRepository.existsByUsername(request.getUsername())) {
			throw new InvalidCredentialsException(ErrorCode.USER_ALREADY_EXISTS);
		}
		
		UserEntity user = UserEntity.builder()
				.username(request.getUsername())
				.password(this.passwordEncoder.encode(request.getPassword()))
				.role(Role.valueOf(request.getRole()))
				.build();
		
		return this.userRepository.save(user);
	}
	
	@Override
	public String signIn(User.SignIn request) {
		UserEntity user = this.userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new InvalidCredentialsException(ErrorCode.USER_NOT_FOUND));
		
		if (!this.passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new InvalidCredentialsException(ErrorCode.INVALID_CREDENTIALS);
		}
		
		try {
			Authentication authentication = this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			
			return this.tokenProvider.createToken(authentication.getName(), authentication.getAuthorities());
		} catch (AuthenticationException e) {
			throw new InvalidCredentialsException(ErrorCode.INVALID_CREDENTIALS);
		}
	}
	
}
