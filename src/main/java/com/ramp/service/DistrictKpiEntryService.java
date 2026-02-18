package com.ramp.service;

import com.ramp.dto.DistrictKpiEntryDto;
import com.ramp.dto.StateTotalDto;
import com.ramp.entity.DistrictKpiEntry;
import com.ramp.repo.DistrictKpiEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DistrictKpiEntryService {

    private final DistrictKpiEntryRepository repository;

    public DistrictKpiEntryDto create(DistrictKpiEntryDto dto) {
        DistrictKpiEntry entity = toEntity(dto);
        DistrictKpiEntry saved = repository.save(entity);
        return toDto(saved);
    }

    public List<DistrictKpiEntryDto> getAll() {
        return repository.findAllByOrderByDistrictNameAsc()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public StateTotalDto getStateTotal() {
        return StateTotalDto.builder()
                .totalSc(repository.sumSc())
                .totalSt(repository.sumSt())
                .totalObc(repository.sumObc())
                .totalGeneralCategory(repository.sumGeneralCategory())
                .totalWomen(repository.sumWomen())
                .grandTotal(repository.sumTotal())
                .build();
    }

    private DistrictKpiEntryDto toDto(DistrictKpiEntry entity) {
        return DistrictKpiEntryDto.builder()
                .id(entity.getId())
                .districtName(entity.getDistrictName())
                .sc(entity.getSc())
                .st(entity.getSt())
                .obc(entity.getObc())
                .generalCategory(entity.getGeneralCategory())
                .women(entity.getWomen())
                .total(entity.getTotal())
                .financialYear(entity.getFinancialYear())
                .quarter(entity.getQuarter())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private DistrictKpiEntry toEntity(DistrictKpiEntryDto dto) {
        return DistrictKpiEntry.builder()
                .districtName(dto.getDistrictName())
                .sc(dto.getSc())
                .st(dto.getSt())
                .obc(dto.getObc())
                .generalCategory(dto.getGeneralCategory())
                .women(dto.getWomen())
                .financialYear(dto.getFinancialYear())
                .quarter(dto.getQuarter())
                .build();
    }
}
