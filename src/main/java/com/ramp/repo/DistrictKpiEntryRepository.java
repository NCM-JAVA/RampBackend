package com.ramp.repo;

import com.ramp.entity.DistrictKpiEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
