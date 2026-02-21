package com.ramp.service;

import com.ramp.dto.*;
import com.ramp.repo.DistrictKpiEntryRepository;
import com.ramp.repo.KpiEntryRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DistrictKpiEntryRepository districtRepo;
    private final KpiEntryRepository kpiRepo;

    public DashboardResponseDTO getDashboard(
            String year,
            String intervention,
            String district) {

        Long totalBeneficiaries =
                districtRepo.getTotalBeneficiaries(year, district);

        Long events =
                kpiRepo.getEvents(year, intervention);

        Long msmes =
                kpiRepo.countByKeyword(year, intervention, "MSME");

        Long jobs =
                kpiRepo.countByKeyword(year, intervention, "Job");

        List<DistributionDTO> distribution =
                kpiRepo.getDistribution(year, intervention)
                        .stream()
                        .map(obj -> new DistributionDTO(
                                obj[0].toString(),
                                (Long) obj[1]))
                        .collect(Collectors.toList());

        List<DistrictDTO> districtData =
                districtRepo.getDistrictWise(year)
                        .stream()
                        .map(obj -> new DistrictDTO(
                                obj[0].toString(),
                                (Long) obj[1]))
                        .collect(Collectors.toList());

        return DashboardResponseDTO.builder()
                .totalBeneficiaries(totalBeneficiaries != null ? totalBeneficiaries : 0)
                .msmesBenefited(msmes != null ? msmes : 0)
                .jobsSupported(jobs != null ? jobs : 0)
                .eventsConducted(events != null ? events : 0)
                .distribution(distribution)
                .districtWiseData(districtData)
                .build();
    }
}