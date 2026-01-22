package com.ramp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ramp.entity.IndustrialUnitRegistration;
import com.ramp.service.IndustrialUnitRegistrationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/industrial-unit")
@RequiredArgsConstructor
public class IndustrialUnitRegistrationController {

    private final IndustrialUnitRegistrationService service;

    @PostMapping("/register")
    public ResponseEntity<IndustrialUnitRegistration> register(
            @RequestBody IndustrialUnitRegistration registration) {

        IndustrialUnitRegistration saved = service.create(registration);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

	public static IndustrialUnitRegistration create(IndustrialUnitRegistration registration) {
		// TODO Auto-generated method stub
		return null;
	}
}
