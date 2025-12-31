package com.ramp.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;

public class LoginReq {
    @NotBlank
    private String userName;

    @NotBlank
    private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginReq [userName=" + userName + ", password=" + password + "]";
	}

    // getters and setters
    
    
}