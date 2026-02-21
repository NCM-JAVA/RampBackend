package com.ramp.service;

import com.ramp.entity.Intervention;
import com.ramp.entity.Kpi;
import com.ramp.repo.ActivityRepository;
import com.ramp.repo.ComponentRepository;
import com.ramp.repo.InterventionRepository;
import com.ramp.repo.KpiRepository;
import com.ramp.entity.Component;
import com.ramp.entity.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MasterService {

    private final InterventionRepository interventionRepository;
    private final ComponentRepository componentRepository;
    private final ActivityRepository activityRepository;

    private final KpiRepository kpiRepository;

 // Get All KPI
 public List<Kpi> getAllKpis() {
     return kpiRepository.findAll();
 }

 // Get KPI by Activity
 public List<Kpi> getKpiByActivity(String activityNo) {
     return kpiRepository.findByActivity_ActivityNo(activityNo);
 }

 // Save KPI
 public Kpi saveKpi(Kpi kpi) {
     return kpiRepository.save(kpi);
 }
    // Intervention
    

    public List<Intervention> getAllInterventions() {
        return interventionRepository.findAll();
    }

    public Intervention saveIntervention(Intervention intervention) {
        return interventionRepository.save(intervention);
    }

   
    // Component
    

    public List<Component> getAllComponents() {
        return componentRepository.findAll();
    }

    public List<Component> getComponentsByIntervention(String interventionNo) {
        return componentRepository.findByIntervention_InterventionNo(interventionNo);
    }

    public Component saveComponent(Component component) {
        return componentRepository.save(component);
    }

  
    // Activity
   

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public List<Activity> getActivitiesByComponent(String componentNo) {
        return activityRepository.findByComponent_ComponentNo(componentNo);
    }

    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }
}