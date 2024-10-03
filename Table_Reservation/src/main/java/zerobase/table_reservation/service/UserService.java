package zerobase.table_reservation.service;

import zerobase.table_reservation.model.User;
import zerobase.table_reservation.persist.entity.UserEntity;

public interface UserService {
	
	UserEntity signUp(User.SignUp request);
	
	String signIn(User.SignIn request);
	
}
