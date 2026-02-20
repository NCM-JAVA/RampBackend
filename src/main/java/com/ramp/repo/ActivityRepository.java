package com.ramp.repo;

import com.ramp.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {

    // Get activities by component
    List<Activity> findByComponent_ComponentNo(String componentNo);

    // Optional search
    Activity findByActivities(String activities);
}