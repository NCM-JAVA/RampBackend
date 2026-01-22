package com.ramp.controller;

import java.util.HashMap;
import java.util.Map;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramp.entity.Users;
import com.ramp.req.LoginReq;
import com.ramp.req.UserReq;
import com.ramp.res.LoginResponse;
import com.ramp.res.StatusResponse;
import com.ramp.res.UserResponse;
import com.ramp.service.UserService;
import com.ramp.utils.JwtUtil;
import com.ramp.utils.StatusCode;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    
    @PostMapping("/register")
    public ResponseEntity<StatusResponse<UserResponse>> register(
            @RequestBody UserReq req) {   // ❌ removed @Valid
        StatusResponse<UserResponse> response = userService.registerUser(req);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

 /*   @PostMapping("/register")
    public ResponseEntity<StatusResponse<UserResponse>> register(@Valid @RequestBody UserReq req) {
        StatusResponse<UserResponse> response = userService.registerUser(req);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    } */

    @PostMapping("/login")
    public ResponseEntity<StatusResponse<LoginResponse>> login(@Valid @RequestBody LoginReq req) {
        StatusResponse<LoginResponse> response = userService.login(req.getLogin(), req.getPassword(), jwtUtil);
        return ResponseEntity.ok(response);
    }

}

