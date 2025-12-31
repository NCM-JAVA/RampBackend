package com.ramp.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.*;




@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="user_id")
    private Long userId;
    private String userName;
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String status;
    
    private String enterpriseName;
    
    private String fullName;
    
    private String enterpriseActivity;
    
    private String enterpriseCity;
    
    private String enterpriseAddress;
    private String gstNumber;
    
    
    private String udyamUapNumber;
    
    private String businessType;
    
    private Boolean acceptTerms;

    
    @Column(name = "created_by")
    private Long createdBy;


    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active = true;

    private String mobileNumber;
    private String panNumber;
    private String otp;
    private String address;
    private String email;
    private String phoneNumber;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
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
	public RoleEntity getRole() {
		return role;
	}
	public void setRole(RoleEntity role) {
		this.role = role;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	public Boolean getAcceptTerms() {
		return acceptTerms;
	}
	public void setAcceptTerms(Boolean acceptTerms) {
		this.acceptTerms = acceptTerms;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "Users [userId=" + userId + ", userName=" + userName + ", password=" + password + ", role=" + role
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", status=" + status
				+ ", enterpriseName=" + enterpriseName + ", fullName=" + fullName + ", enterpriseActivity="
				+ enterpriseActivity + ", enterpriseCity=" + enterpriseCity + ", enterpriseAddress=" + enterpriseAddress
				+ ", gstNumber=" + gstNumber + ", udyamUapNumber=" + udyamUapNumber + ", businessType=" + businessType
				+ ", acceptTerms=" + acceptTerms + ", createdBy=" + createdBy + ", active=" + active + ", mobileNumber="
				+ mobileNumber + ", panNumber=" + panNumber + ", otp=" + otp + ", address=" + address + ", email="
				+ email + ", phoneNumber=" + phoneNumber + "]";
	}
	
	    

}
