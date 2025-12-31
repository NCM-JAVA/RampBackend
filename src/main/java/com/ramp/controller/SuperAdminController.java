package com.ramp.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ramp.req.AdminRegistrationReq;
import com.ramp.req.CMSRegistrationReq;
import com.ramp.res.StatusResponse;
import com.ramp.res.UserResponse;
import com.ramp.service.UserService;

@RestController
@RequestMapping("/api/superadmin")
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
public class SuperAdminController {

    private final UserService userService;

    public SuperAdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register-admin")
    public ResponseEntity<StatusResponse<UserResponse>> registerAdmin(@Valid @RequestBody AdminRegistrationReq req) {
    	
    	System.out.println("req==>"+req);
        StatusResponse<UserResponse> response = userService.registerAdmin(req);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PostMapping("/register-content-manager")
    public ResponseEntity<StatusResponse<UserResponse>> registerContentManager(@Valid @RequestBody CMSRegistrationReq req) {
    	
        StatusResponse<UserResponse> response = userService.registerCMS(req);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<StatusResponse<List<UserResponse>>> getAllUsers() {
        StatusResponse<List<UserResponse>> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/role/{role}")
    public ResponseEntity<StatusResponse<List<UserResponse>>> getUsersByRole(@PathVariable String role) {
        StatusResponse<List<UserResponse>> response = userService.getUsersByRole(role);
        return ResponseEntity.ok(response);
    }
}
