package com.ramp.utils;

import org.springframework.stereotype.Component;

import com.ramp.entity.Users;
import com.ramp.req.UserReq;
import com.ramp.res.UserResponse;
@Component
public class UserMapper {

    public Users toEntity(UserReq req) {
        Users user = new Users();
        user.setUserName(req.getLoginUsername());
        user.setEmail(req.getAuthorizedEmail());
        user.setMobileNumber(req.getAuthorizedMobile());
        user.setPanNumber(req.getAuthorizedPAN());
        user.setEnterpriseActivity(req.getEnterpriseActivity());
        user.setEnterpriseCity(req.getEnterpriseCity());
        user.setEnterpriseAddress(req.getEnterpriseAddress());
        user.setUdyamUapNumber(req.getUdyamUapNumber());
        user.setBusinessType(req.getBusinessType());
        user.setAcceptTerms(req.getAcceptTerms());
        user.setFullName(req.getAuthorizedPerson());
        user.setEnterpriseName(req.getEnterpriseName());
        user.setGstNumber(req.getGstNumber());
        return user;
    }

    public UserResponse toResponse(Users user) {
        UserResponse dto = new UserResponse();
        dto.setLoginUsername(user.getUserName());
        dto.setAuthorizedEmail(user.getEmail());
        dto.setAuthorizedMobile(user.getMobileNumber());
        dto.setAuthorizedPAN(user.getPanNumber());
        dto.setEnterpriseActivity(user.getEnterpriseActivity());
        dto.setEnterpriseCity(user.getEnterpriseCity());
        dto.setEnterpriseAddress(user.getEnterpriseAddress());
        dto.setUdyamUapNumber(user.getUdyamUapNumber());
        dto.setBusinessType(user.getBusinessType());
        dto.setEnterpriseName(user.getEnterpriseName());
        dto.setAuthorizedPerson(user.getFullName());
        dto.setAcceptTerms(user.getAcceptTerms());
        dto.setRole(user.getRole() != null ? user.getRole().getRoleName() : null);
        dto.setGstNumber(user.getGstNumber());
        return dto;
    }


}