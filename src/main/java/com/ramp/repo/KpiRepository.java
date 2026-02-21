package com.ramp.repo;

import com.ramp.entity.Kpi;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface KpiRepository extends JpaRepository<Kpi, String> {

    List<Kpi> findByActivity_ActivityNo(String activityNo);
}