package com.ramp.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ramp.entity.IndustrialUnitRegistration;
import com.ramp.enums.ApplicationStatus;

public interface IndustrialUnitRegistrationRepository
        extends JpaRepository<IndustrialUnitRegistration, Long> {

    List<IndustrialUnitRegistration> findByUserIdOrderByCreatedAtDesc(String userId);

    List<IndustrialUnitRegistration> findByStatusOrderByCreatedAtDesc(ApplicationStatus status);

    List<IndustrialUnitRegistration> findAllByOrderByCreatedAtDesc();

    long countByStatus(ApplicationStatus status);

	Long countByUserId(String userId);

	Long countByUserIdAndStatus(String userId, ApplicationStatus submitted);

	@Modifying
	@Query("UPDATE IndustrialUnitRegistration i SET i.status = :status WHERE i.id = :id")
	int updateStatus(@Param("id") Long id, @Param("status") ApplicationStatus status);

	Optional<IndustrialUnitRegistration> findOne(Specification<IndustrialUnitRegistration> forSingleRecord);
}
