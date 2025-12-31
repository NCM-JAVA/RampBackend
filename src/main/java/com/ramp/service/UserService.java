package com.ramp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ramp.entity.Users;
import com.ramp.req.AdminRegistrationReq;
import com.ramp.req.CMSRegistrationReq;
import com.ramp.req.UserReq;
import com.ramp.res.LoginResponse;
import com.ramp.res.StatusResponse;
import com.ramp.res.UserResponse;
import com.ramp.utils.JwtUtil;



public interface UserService {
    StatusResponse<UserResponse> registerUser(UserReq req);
    StatusResponse<UserResponse> registerCMS(CMSRegistrationReq req);
    StatusResponse<UserResponse> registerAdmin(AdminRegistrationReq req);
    StatusResponse<LoginResponse> login(String username, String password, JwtUtil jwtUtil);
    StatusResponse<List<UserResponse>> getAllUsers();
    StatusResponse<List<UserResponse>> getUsersByRole(String role);
}