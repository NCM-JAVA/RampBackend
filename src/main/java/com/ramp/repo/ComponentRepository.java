package com.ramp.repo;

import com.ramp.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, String> {

    // Get components by intervention
    List<Component> findByIntervention_InterventionNo(String interventionNo);

    // Optional: search by name
    Component findByComponent(String component);
}