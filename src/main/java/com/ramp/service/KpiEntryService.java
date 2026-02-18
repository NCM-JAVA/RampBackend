package com.ramp.service;

import com.ramp.dto.KpiEntryDto;
import com.ramp.entity.KpiEntry;
import com.ramp.repo.KpiEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KpiEntryService {

    private final KpiEntryRepository repository;

    public KpiEntryDto create(KpiEntryDto dto) {
        KpiEntry entity = toEntity(dto);
        KpiEntry saved = repository.save(entity);
        return toDto(saved);
    }

    public List<KpiEntryDto> getAll() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public KpiEntryDto getById(Long id) {
        KpiEntry entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("KPI entry not found with id: " + id));
        return toDto(entity);
    }

    public KpiEntryDto update(Long id, KpiEntryDto dto) {
        KpiEntry entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("KPI entry not found with id: " + id));

        entity.setFinancialYear(dto.getFinancialYear());
        entity.setQuarter(dto.getQuarter());
        entity.setIntervention(dto.getIntervention());
        entity.setComponent(dto.getComponent());
        entity.setKpi(dto.getKpi());
        entity.setActivity(dto.getActivity());

        KpiEntry saved = repository.save(entity);
        return toDto(saved);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("KPI entry not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private KpiEntryDto toDto(KpiEntry entity) {
        return KpiEntryDto.builder()
                .id(entity.getId())
                .financialYear(entity.getFinancialYear())
                .quarter(entity.getQuarter())
                .intervention(entity.getIntervention())
                .component(entity.getComponent())
                .kpi(entity.getKpi())
                .activity(entity.getActivity())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private KpiEntry toEntity(KpiEntryDto dto) {
        return KpiEntry.builder()
                .financialYear(dto.getFinancialYear())
                .quarter(dto.getQuarter())
                .intervention(dto.getIntervention())
                .component(dto.getComponent())
                .kpi(dto.getKpi())
                .activity(dto.getActivity())
                .build();
    }
}
