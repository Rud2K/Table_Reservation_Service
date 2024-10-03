package zerobase.table_reservation.model;

import lombok.Data;

public class User {
	
	@Data
	public static class SignUp {
		private String username;
		private String password;
		private String role;
	}
	
	@Data
	public static class SignIn {
		private String username;
		private String password;
	}
	
}
