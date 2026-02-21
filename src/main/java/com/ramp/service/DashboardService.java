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

//    public DashboardResponseDTO getDashboard(
//            String year,
//            String intervention,
//            String district) {
//
//        Long totalBeneficiaries =
//                districtRepo.getTotalBeneficiaries(year, district);
//
//        Long events =
//                kpiRepo.getEvents(year, intervention);
//
//        Long msmes =
//                kpiRepo.countByKeyword(year, intervention, "MSME");
//
//        Long jobs =
//                kpiRepo.countByKeyword(year, intervention, "Job");
//
//        List<DistributionDTO> distribution =
//                kpiRepo.getDistribution(year, intervention)
//                        .stream()
//                        .map(obj -> new DistributionDTO(
//                                obj[0].toString(),
//                                (Long) obj[1]))
//                        .collect(Collectors.toList());
//
//        List<DistrictDTO> districtData =
//                districtRepo.getDistrictWise(year)
//                        .stream()
//                        .map(obj -> new DistrictDTO(
//                                obj[0].toString(),
//                                (Long) obj[1]))
//                        .collect(Collectors.toList());
//
//        return DashboardResponseDTO.builder()
//                .totalBeneficiaries(totalBeneficiaries != null ? totalBeneficiaries : 0)
//                .msmesBenefited(msmes != null ? msmes : 0)
//                .jobsSupported(jobs != null ? jobs : 0)
//                .eventsConducted(events != null ? events : 0)
//                .distribution(distribution)
//                .districtWiseData(districtData)
//                .build();
//    }
    
    
    
    public DashboardResponseDTO getDashboard(
            String year,
            String intervention,
            String district) {

        // ===============================
        // 1️⃣ TOTAL BENEFICIARIES
        // ===============================
        Long totalBeneficiaries =
                districtRepo.getTotalBeneficiaries(year, district);

        // ===============================
        // 2️⃣ EVENTS
        // ===============================
        Long events =
                kpiRepo.getEvents(year, intervention);

        // ===============================
        // 3️⃣ MSME & JOB COUNT
        // ===============================
        Long msmes =
                kpiRepo.countByKeyword(year, intervention, "MSME");

        Long jobs =
                kpiRepo.countByKeyword(year, intervention, "Job");

         // 4️⃣ DISTRIBUTION (Component Wise)
         List<DistributionDTO> distribution =
                kpiRepo.getDistribution(year, intervention)
                        .stream()
                        .map(obj -> new DistributionDTO(
                                obj[0].toString(),
                                ((Number) obj[1]).longValue()))
                        .collect(Collectors.toList());

         // 5️⃣ DISTRICT WISE DATA
         List<DistrictDTO> districtData =
                districtRepo.getDistrictWise(year, district)
                        .stream()
                        .map(obj -> new DistrictDTO(
                                obj[0].toString(),
                                ((Number) obj[1]).longValue()))
                        .collect(Collectors.toList());

         // 6️⃣ SOCIAL COMPOSITION (VALUES ONLY)
         List<Object[]> result =
                districtRepo.getSocialComposition(year, district);

        Long sc = 0L;
        Long st = 0L;
        Long obc = 0L;
        Long general = 0L;
        Long women = 0L;
        Long total = 0L;

        if (result != null && !result.isEmpty()) {

            Object[] socialRaw = result.get(0);

            sc = socialRaw[0] == null ? 0L : ((Number) socialRaw[0]).longValue();
            st = socialRaw[1] == null ? 0L : ((Number) socialRaw[1]).longValue();
            obc = socialRaw[2] == null ? 0L : ((Number) socialRaw[2]).longValue();
            general = socialRaw[3] == null ? 0L : ((Number) socialRaw[3]).longValue();
            women = socialRaw[4] == null ? 0L : ((Number) socialRaw[4]).longValue();
            total = socialRaw[5] == null ? 0L : ((Number) socialRaw[5]).longValue();
        }

        SocialCompositionDTO socialComposition =
                SocialCompositionDTO.builder()
                        .total(total)
                        .sc(sc)
                        .st(st)
                        .obc(obc)
                        .general(general)
                        .women(women)
                        .build();

         // FINAL RESPONSE
         
        return DashboardResponseDTO.builder()
                .totalBeneficiaries(totalBeneficiaries != null ? totalBeneficiaries : 0L)
                .msmesBenefited(msmes != null ? msmes : 0L)
                .jobsSupported(jobs != null ? jobs : 0L)
                .eventsConducted(events != null ? events : 0L)
                .distribution(distribution)
                .districtWiseData(districtData)
                .socialComposition(socialComposition)
                .build();
    }   
}