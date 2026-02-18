package com.ramp.repo;

import com.ramp.entity.IncentiveApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IncentiveApplicationRepository extends JpaRepository<IncentiveApplication, String> {

    Page<IncentiveApplication> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<IncentiveApplication> findByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    boolean existsByUserId(String userId);

    @Query("SELECT COUNT(i) FROM IncentiveApplication i WHERE i.id LIKE :prefix%")
    long countByIdStartingWith(@Param("prefix") String prefix);
}
