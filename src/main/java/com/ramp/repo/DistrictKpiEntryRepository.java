package com.ramp.repo;

import com.ramp.entity.DistrictKpiEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DistrictKpiEntryRepository extends JpaRepository<DistrictKpiEntry, Long> {

    List<DistrictKpiEntry> findAllByOrderByDistrictNameAsc();

    @Query("SELECT COALESCE(SUM(d.sc), 0) FROM DistrictKpiEntry d")
    Long sumSc();

    @Query("SELECT COALESCE(SUM(d.st), 0) FROM DistrictKpiEntry d")
    Long sumSt();

    @Query("SELECT COALESCE(SUM(d.obc), 0) FROM DistrictKpiEntry d")
    Long sumObc();

    @Query("SELECT COALESCE(SUM(d.generalCategory), 0) FROM DistrictKpiEntry d")
    Long sumGeneralCategory();

    @Query("SELECT COALESCE(SUM(d.women), 0) FROM DistrictKpiEntry d")
    Long sumWomen();

    @Query("SELECT COALESCE(SUM(d.total), 0) FROM DistrictKpiEntry d")
    Long sumTotal();
    
    
    
    
    
    
    
    
    
    
    
    @Query("SELECT SUM(d.total) FROM DistrictKpiEntry d " +
            "WHERE d.financialYear = :year " +
            "AND (:district IS NULL OR d.districtName = :district)")
     Long getTotalBeneficiaries(@Param("year") String year,
                                @Param("district") String district);

     // District-wise (bar chart)
     @Query("SELECT d.districtName, SUM(d.total) FROM DistrictKpiEntry d " +
            "WHERE d.financialYear = :year " +
            "GROUP BY d.districtName")
     List<Object[]> getDistrictWise(@Param("year") String year);
    
    
    
    
    
    
}
