package com.ramp.res;

import com.ramp.enums.RoleType;

public class UserResponse {

    private String enterpriseName;
    private String authorizedPerson;
    private String authorizedEmail;
    private String authorizedPAN;
    private String authorizedMobile;
    private String enterpriseActivity;
    private String enterpriseCity;
    private String enterpriseAddress;
    private String loginUsername;
    private String gstNumber;
    private String udyamUapNumber;
    private String businessType;
    private RoleType role;
    private Boolean acceptTerms;
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
	@Override
	public String toString() {
		return "UserResponse [enterpriseName=" + enterpriseName + ", authorizedPerson=" + authorizedPerson
				+ ", authorizedEmail=" + authorizedEmail + ", authorizedPAN=" + authorizedPAN + ", authorizedMobile="
				+ authorizedMobile + ", enterpriseActivity=" + enterpriseActivity + ", enterpriseCity=" + enterpriseCity
				+ ", enterpriseAddress=" + enterpriseAddress + ", loginUsername=" + loginUsername + ", gstNumber="
				+ gstNumber + ", udyamUapNumber=" + udyamUapNumber + ", businessType=" + businessType + ", role=" + role
				+ ", acceptTerms=" + acceptTerms + "]";
	}

 
}
