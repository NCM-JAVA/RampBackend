package com.ramp.repo;

import com.ramp.entity.KpiEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KpiEntryRepository extends JpaRepository<KpiEntry, Long> {

    List<KpiEntry> findAllByOrderByCreatedAtDesc();
}
