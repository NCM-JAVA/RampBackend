package com.ramp.controller;

import com.ramp.dto.DistrictKpiEntryDto;
import com.ramp.dto.StateTotalDto;
import com.ramp.service.DistrictKpiEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/district-kpi")
@RequiredArgsConstructor
public class DistrictKpiEntryController {

    private final DistrictKpiEntryService service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DistrictKpiEntryDto dto) {
        try {
            DistrictKpiEntryDto created = service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<DistrictKpiEntryDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/state-total")
    public ResponseEntity<StateTotalDto> getStateTotal() {
        return ResponseEntity.ok(service.getStateTotal());
    }
}
