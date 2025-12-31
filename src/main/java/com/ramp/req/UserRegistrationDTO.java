package com.ramp.req;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ramp.enums.RoleType;

public class UserRegistrationDTO {
    @NotBlank(message = "Name is required")
    private String name;
    
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    @NotBlank(message = "Mobile is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile must be 10 digits")
    private String mobile;    
    @NotNull(message = "Role is required")
    private RoleType role;
    
    
    
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getMobile() {
		return mobile;
	}



	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	public RoleType getRole() {
		return role;
	}



	public void setRole(RoleType role) {
		this.role = role;
	}



	@Override
	public String toString() {
		return "UserRegistrationDTO [name=" + name + ", email=" + email + ", password=" + password + ", mobile="
				+ mobile + ", role=" + role + "]";
	}
    
    
}