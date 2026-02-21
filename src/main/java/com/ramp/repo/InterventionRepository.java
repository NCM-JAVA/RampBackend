package com.ramp.repo;

import com.ramp.entity.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterventionRepository extends JpaRepository<Intervention, String> {

    // Optional: search by name
    Intervention findByIntervention(String intervention);
}