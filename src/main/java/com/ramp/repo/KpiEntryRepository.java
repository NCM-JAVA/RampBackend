package com.ramp.repo;

import com.ramp.entity.KpiEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KpiEntryRepository extends JpaRepository<KpiEntry, Long> {

    List<KpiEntry> findAllByOrderByCreatedAtDesc();
    
    
    @Query("SELECT COUNT(k) FROM KpiEntry k " +
            "WHERE k.financialYear = :year " +
            "AND (:intervention IS NULL OR k.intervention = :intervention) " +
            "AND k.kpi LIKE %:keyword%")
     Long countByKeyword(@Param("year") String year,
                         @Param("intervention") String intervention,
                         @Param("keyword") String keyword);


     @Query("SELECT COUNT(k) FROM KpiEntry k " +
            "WHERE k.financialYear = :year " +
            "AND (:intervention IS NULL OR k.intervention = :intervention)")
     Long getEvents(@Param("year") String year,
                    @Param("intervention") String intervention);


     @Query("SELECT k.component, COUNT(k) FROM KpiEntry k " +
            "WHERE k.financialYear = :year " +
            "AND (:intervention IS NULL OR k.intervention = :intervention) " +
            "GROUP BY k.component")
     List<Object[]> getDistribution(@Param("year") String year,
                                    @Param("intervention") String intervention);

    
    
    
    
}
