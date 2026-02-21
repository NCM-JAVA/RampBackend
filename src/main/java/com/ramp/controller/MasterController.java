package com.ramp.controller;

import com.ramp.entity.Intervention;
import com.ramp.entity.Kpi;
import com.ramp.entity.Component;
import com.ramp.entity.Activity;
import com.ramp.service.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/master")
@RequiredArgsConstructor
@CrossOrigin
public class MasterController {

    private final MasterService masterService;

   
    // Intervention APIs
   

    @GetMapping("/allInterventions")
    public List<Intervention> getInterventions() {
        return masterService.getAllInterventions();
    }

//    @PostMapping("/interventions")
//    public Intervention saveIntervention(@RequestBody Intervention intervention) {
//        return masterService.saveIntervention(intervention);
//    }

    // Component APIs
   

//    @GetMapping("/components")
//    public List<Component> getComponents() {
//        return masterService.getAllComponents();
//    }

    @GetMapping("/getComponents/{interventionNo}")
    public List<Component> getComponentsByIntervention(@PathVariable String interventionNo) {
        return masterService.getComponentsByIntervention(interventionNo);
    }

//    @PostMapping("/components")
//    public Component saveComponent(@RequestBody Component component) {
//        return masterService.saveComponent(component);
//    }

   
    // Activity APIs
  

//    @GetMapping("/activities")
//    public List<Activity> getActivities() {
//        return masterService.getAllActivities();
//    }

    @GetMapping("/getActivities/{componentNo}")
    public List<Activity> getActivitiesByComponent(@PathVariable String componentNo) {
        return masterService.getActivitiesByComponent(componentNo);
    }

//    @PostMapping("/activities")
//    public Activity saveActivity(@RequestBody Activity activity) {
//        return masterService.saveActivity(activity);
//    }
// // =============================
 // KPI APIs
 // =============================

// @GetMapping("/kpis")
// public List<Kpi> getKpis() {
//     return masterService.getAllKpis();
// }

 @GetMapping("/kpis/{activityNo}")
 public List<Kpi> getKpiByActivity(@PathVariable String activityNo) {
     return masterService.getKpiByActivity(activityNo);
 }

// @PostMapping("/kpis")
// public Kpi saveKpi(@RequestBody Kpi kpi) {
//     return masterService.saveKpi(kpi);
// }
    
    
}