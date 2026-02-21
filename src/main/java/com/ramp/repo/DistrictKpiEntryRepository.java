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
    
    
  
    
    
    @Query("SELECT d.districtName, " +
    	       "SUM(d.sc + d.st + d.obc + d.generalCategory + d.women) " +
    	       "FROM DistrictKpiEntry d " +
    	       "WHERE d.financialYear = :year " +
    	       "AND (:district IS NULL OR LOWER(d.districtName) = LOWER(:district)) " +
    	       "GROUP BY d.districtName")
    	List<Object[]> getDistrictWise1(String year, String district);
    
    	@Query("SELECT " +
    		       "COALESCE(SUM(COALESCE(d.sc,0)),0), " +
    		       "COALESCE(SUM(COALESCE(d.st,0)),0), " +
    		       "COALESCE(SUM(COALESCE(d.obc,0)),0), " +
    		       "COALESCE(SUM(COALESCE(d.generalCategory,0)),0), " +
    		       "COALESCE(SUM(COALESCE(d.women,0)),0), " +
    		       "COALESCE(SUM(COALESCE(d.sc,0) + COALESCE(d.st,0) + " +
    		       "COALESCE(d.obc,0) + COALESCE(d.generalCategory,0) + COALESCE(d.women,0)),0) " +
    		       "FROM DistrictKpiEntry d " +
    		       "WHERE d.financialYear = :year " +
    		       "AND LOWER(d.districtName) = LOWER(:district)")
    		Object[] getSocialComposition6(String year, String district);
    	@Query("SELECT " +
    		       "COALESCE(SUM(d.sc),0), " +
    		       "COALESCE(SUM(d.st),0), " +
    		       "COALESCE(SUM(d.obc),0), " +
    		       "COALESCE(SUM(d.generalCategory),0), " +
    		       "COALESCE(SUM(d.women),0), " +
    		       "COALESCE(SUM(d.sc + d.st + d.obc + d.generalCategory + d.women),0) " +
    		       "FROM DistrictKpiEntry d " +
    		       "WHERE (:year IS NULL OR d.financialYear = :year) " +
    		       "AND (:district IS NULL OR LOWER(d.districtName) = LOWER(:district))")
    		Object[] getSocialComposition5(String year, String district);
    
    @Query("SELECT SUM(d.total) FROM DistrictKpiEntry d " +
            "WHERE d.financialYear = :year " +
            "AND (:district IS NULL OR d.districtName = :district)")
     Long getTotalBeneficiaries1(@Param("year") String year,
                                @Param("district") String district);

     // District-wise (bar chart)
     @Query("SELECT d.districtName, SUM(d.total) FROM DistrictKpiEntry d " +
            "WHERE d.financialYear = :year " +
            "GROUP BY d.districtName")
     List<Object[]> getDistrictWise(@Param("year") String year);
    
    
   
     // 1️⃣ TOTAL BENEFICIARIES
     @Query("SELECT COALESCE(SUM(d.sc + d.st + d.obc + d.generalCategory + d.women),0) " +
            "FROM DistrictKpiEntry d " +
            "WHERE (:year IS NULL OR d.financialYear = :year) " +
            "AND (:district IS NULL OR LOWER(d.districtName) = LOWER(:district))")
     Long getTotalBeneficiaries(@Param("year") String year,
                                @Param("district") String district);


     // 2️⃣ DISTRICT WISE TOTAL
     @Query("SELECT d.districtName, " +
            "COALESCE(SUM(d.sc + d.st + d.obc + d.generalCategory + d.women),0) " +
            "FROM DistrictKpiEntry d " +
            "WHERE (:year IS NULL OR d.financialYear = :year) " +
            "AND (:district IS NULL OR LOWER(d.districtName) = LOWER(:district)) " +
            "GROUP BY d.districtName")
     List<Object[]> getDistrictWise(@Param("year") String year,
                                    @Param("district") String district);


     // 3️⃣ SOCIAL COMPOSITION (VALUE ONLY)
     @Query("SELECT " +
            "COALESCE(SUM(d.sc),0), " +
            "COALESCE(SUM(d.st),0), " +
            "COALESCE(SUM(d.obc),0), " +
            "COALESCE(SUM(d.generalCategory),0), " +
            "COALESCE(SUM(d.women),0), " +
            "COALESCE(SUM(d.sc + d.st + d.obc + d.generalCategory + d.women),0) " +
            "FROM DistrictKpiEntry d " +
            "WHERE (:year IS NULL OR d.financialYear = :year) " +
            "AND (:district IS NULL OR LOWER(d.districtName) = LOWER(:district))")
     Object[] getSocialComposition3(@Param("year") String year,
                                   @Param("district") String district);
     @Query(value = "SELECT " +
    	       "SUM(sc), SUM(st), SUM(obc), " +
    	       "SUM(general_category), SUM(women), " +
    	       "SUM(sc + st + obc + general_category + women) " +
    	       "FROM district_kpi_entries " +
    	       "WHERE financial_year = :year " +
    	       "AND district_name = :district",
    	       nativeQuery = true)
    	Object[] getSocialComposition7(String year, String district);
    
     @Query(value = "SELECT " +
    	        "SUM(sc), " +
    	        "SUM(st), " +
    	        "SUM(obc), " +
    	        "SUM(general_category), " +
    	        "SUM(women), " +
    	        "SUM(sc + st + obc + general_category + women) " +
    	        "FROM district_kpi_entries " +
    	        "WHERE financial_year = :year " +
    	        "AND district_name = :district",
    	        nativeQuery = true)
    	List<Object[]> getSocialComposition11(
    	        @Param("year") String year,
    	        @Param("district") String district);
    	@Query(value = 
    		    "SELECT * FROM (" +
    		    " SELECT " +
    		    "   SUM(sc) AS sc_total, " +
    		    "   SUM(st) AS st_total, " +
    		    "   SUM(obc) AS obc_total, " +
    		    "   SUM(general_category) AS general_total, " +
    		    "   SUM(women) AS women_total, " +
    		    "   SUM(sc + st + obc + general_category + women) AS grand_total " +
    		    " FROM district_kpi_entries " +
    		    " WHERE financial_year = :year " +
    		    " AND district_name = :district" +
    		    ") AS result",
    		    nativeQuery = true)
    		List<Object[]> getSocialComposition(
    		        @Param("year") String year,
    		        @Param("district") String district);
    	
    	
    
}
