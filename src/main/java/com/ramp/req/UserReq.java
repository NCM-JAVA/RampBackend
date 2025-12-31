package com.ramp.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ramp.enums.RoleType;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;




	public class UserReq {

	    @NotBlank(message = "Enterprise name is required")
	    private String enterpriseName;

	    @NotBlank(message = "Authorized person is required")
	    private String authorizedPerson;

	    @Email(message = "Invalid email format")
	    @NotBlank(message = "Email is required")
	    private String authorizedEmail;

	    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN format")
	    private String authorizedPAN;

	    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid mobile number")
	    private String authorizedMobile;

	    @NotBlank(message = "Enterprise activity is required")
	    private String enterpriseActivity;

	    @NotBlank(message = "City is required")
	    private String enterpriseCity;

	    @NotBlank(message = "Address is required")
	    private String enterpriseAddress;

	    @NotBlank(message = "Username is required")
	    private String loginUsername;

	    @Pattern(regexp = "^[0-9A-Z]{15}$", message = "Invalid GST number")
	    private String gstNumber;

	    private String udyamUapNumber;

	    @NotBlank(message = "Business type is required")
	    private String businessType;
	    
	    @NotNull(message = "Role is required")
	    private RoleType role;

	    @AssertTrue(message = "You must accept the terms and conditions")
	    private Boolean acceptTerms;
	    @NotBlank(message = "Password is required")
	    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	    @Pattern(
	        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,20}$",
	        message = "Password must contain at least one uppercase, one lowercase, one digit, and one special character"
	    )
	    private String password;
		public String getEnterpriseName() {
			return enterpriseName;
		}
		public void setEnterpriseName(String enterpriseName) {
			this.enterpriseName = enterpriseName;
		}
		public String getAuthorizedPerson() {
			return authorizedPerson;
		}
		public void setAuthorizedPerson(String authorizedPerson) {
			this.authorizedPerson = authorizedPerson;
		}
		public String getAuthorizedEmail() {
			return authorizedEmail;
		}
		public void setAuthorizedEmail(String authorizedEmail) {
			this.authorizedEmail = authorizedEmail;
		}
		public String getAuthorizedPAN() {
			return authorizedPAN;
		}
		public void setAuthorizedPAN(String authorizedPAN) {
			this.authorizedPAN = authorizedPAN;
		}
		public String getAuthorizedMobile() {
			return authorizedMobile;
		}
		public void setAuthorizedMobile(String authorizedMobile) {
			this.authorizedMobile = authorizedMobile;
		}
		public String getEnterpriseActivity() {
			return enterpriseActivity;
		}
		public void setEnterpriseActivity(String enterpriseActivity) {
			this.enterpriseActivity = enterpriseActivity;
		}
		public String getEnterpriseCity() {
			return enterpriseCity;
		}
		public void setEnterpriseCity(String enterpriseCity) {
			this.enterpriseCity = enterpriseCity;
		}
		public String getEnterpriseAddress() {
			return enterpriseAddress;
		}
		public void setEnterpriseAddress(String enterpriseAddress) {
			this.enterpriseAddress = enterpriseAddress;
		}
		public String getLoginUsername() {
			return loginUsername;
		}
		public void setLoginUsername(String loginUsername) {
			this.loginUsername = loginUsername;
		}
		public String getGstNumber() {
			return gstNumber;
		}
		public void setGstNumber(String gstNumber) {
			this.gstNumber = gstNumber;
		}
		public String getUdyamUapNumber() {
			return udyamUapNumber;
		}
		public void setUdyamUapNumber(String udyamUapNumber) {
			this.udyamUapNumber = udyamUapNumber;
		}
		public String getBusinessType() {
			return businessType;
		}
		public void setBusinessType(String businessType) {
			this.businessType = businessType;
		}
		public RoleType getRole() {
			return role;
		}
		public void setRole(RoleType role) {
			this.role = role;
		}
		public Boolean getAcceptTerms() {
			return acceptTerms;
		}
		public void setAcceptTerms(Boolean acceptTerms) {
			this.acceptTerms = acceptTerms;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		@Override
		public String toString() {
			return "UserReq [enterpriseName=" + enterpriseName + ", authorizedPerson=" + authorizedPerson
					+ ", authorizedEmail=" + authorizedEmail + ", authorizedPAN=" + authorizedPAN
					+ ", authorizedMobile=" + authorizedMobile + ", enterpriseActivity=" + enterpriseActivity
					+ ", enterpriseCity=" + enterpriseCity + ", enterpriseAddress=" + enterpriseAddress
					+ ", loginUsername=" + loginUsername + ", gstNumber=" + gstNumber + ", udyamUapNumber="
					+ udyamUapNumber + ", businessType=" + businessType + ", role=" + role + ", acceptTerms="
					+ acceptTerms + ", password=" + password + "]";
		}

		

	


		
}
