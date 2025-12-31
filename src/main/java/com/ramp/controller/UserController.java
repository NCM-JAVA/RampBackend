package com.ramp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ramp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("users")
@RestController
public class UserController {
	@Autowired
	private UserService userService;
//	
//	@PostMapping("register")
//	public ResponseEntity<StatusResponse> register( @Valid @RequestBody  UserReq userReq ) {
//		  
//		System.out.println(userReq);
//		return userService.register(userReq);
//	}
	
	
	 @GetMapping("/profile")
	    public ResponseEntity<String> getProfile() {
	        return ResponseEntity.ok("User profile info");
	    }
}
