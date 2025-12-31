package com.ramp.res;

public class LoginResponse {

	 private String token;
	    private String type = "Bearer"; // default value
	    private UserResponse user;
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public UserResponse getUser() {
			return user;
		}
		public void setUser(UserResponse user) {
			this.user = user;
		}
		@Override
		public String toString() {
			return "LoginResponse [token=" + token + ", type=" + type + ", user=" + user + "]";
		}
	    
	    
}
