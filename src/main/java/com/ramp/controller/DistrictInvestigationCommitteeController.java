package com.ramp.controller;

import java.time.LocalDateTime;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;

import com.ramp.dto.ApplicationStatusUpdateDTO;
import com.ramp.dto.IndustrialUnitListDTO;
import com.ramp.entity.IndustrialUnitRegistration;
import com.ramp.enums.ApplicationStatus;
import com.ramp.service.RegistrationDashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dic/registrations")
@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
@RequiredArgsConstructor
public class DistrictInvestigationCommitteeController {
	
	
	  

	        private final RegistrationDashboardService service;

	        @GetMapping("/count")
	        public Map<String, Long> getAllCounts() {
	            return service.getOverallCounts();
	        }
/*	        
	        @GetMapping("/count/user")
	        @PreAuthorize("hasAnyAuthority('USER')")
	        public Map<String, Long> getUserCounts(Authentication authentication) {
	            String userId = authentication.getUsername();
	            return service.getUserCounts(userId);
	        }
*/
	    

	        @GetMapping("/list")
	        public Page<IndustrialUnitListDTO> getRegistrations(
	                @RequestParam(required = false) Long unit,
	                @RequestParam(required = false) String unitName,
	                @RequestParam(required = false) String unitLocation,
	                @RequestParam(required = false) ApplicationStatus status,
	                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	                        LocalDateTime fromDate,
	                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	                        LocalDateTime toDate,
	                @RequestParam(defaultValue = "0") int page,
	                @RequestParam(defaultValue = "10") int size,
	                @RequestParam(defaultValue = "createdAt,desc") String sort) {

	            String[] sortParams = sort.split(",");
	            Pageable pageable = PageRequest.of(
	                    page,
	                    size,
	                    Sort.by(
	                        sortParams.length > 1 && sortParams[1].equalsIgnoreCase("asc")
	                            ? Sort.Direction.ASC
	                            : Sort.Direction.DESC,
	                        sortParams[0]
	                    )
	            );

	            return service.getFilteredUnits(
	                    unit,
	                    unitName,
	                    unitLocation,
	                    status,
	                    fromDate,
	                    toDate,
	                    pageable
	            );
	        }
	        
	        @GetMapping("/detail")
	        public IndustrialUnitRegistration getRegistrationDetail(
	                @RequestParam Long id,
	                @RequestParam(required = false) String unitName,
	                @RequestParam(required = false) String unitLocation,
	                @RequestParam(required = false) String gstNo,
	                @RequestParam(required = false) String udyamIemNo,
	                @RequestParam(required = false) ApplicationStatus status) {

	            return service.getSingleRegistration(
	                    id, unitName, unitLocation, gstNo, udyamIemNo, status
	            );
	        }


	        //ANKIT GIT
	        @PutMapping("/{id}/status")
	        @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
	        public void updateRegistrationStatus(
	                @PathVariable Long id,
	                @RequestBody @Valid ApplicationStatusUpdateDTO request) {

	            service.updateStatus(id, request.getStatus());
	        }

	        
}	

	


