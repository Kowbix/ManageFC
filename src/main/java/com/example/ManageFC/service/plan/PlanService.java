package com.example.ManageFC.service.plan;

import com.example.ManageFC.entity.plans.Plan;
import com.example.ManageFC.repository.plan.PlanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@AllArgsConstructor
@Service
public class PlanService {

    private final PlanRepository planRepository;

    public Plan addPlan(Plan plan) {
        return planRepository.save(plan);
    }

    public List<Plan> getPlanByDateAndUserId(LocalDate date, Long userId, LocalTime time){
        List<Plan> plans = planRepository.findPlansByDateAndUserId(date, userId, time);
        Collections.sort(plans, new Comparator<Plan>() {
            @Override
            public int compare(Plan o1, Plan o2) {
                return o1.getTime().compareTo(o2.getTime());
            }
        });
        return plans;
    }

    public List<Plan> getPlansByCompartmentDateAndUserId(LocalDate dateFrom, LocalDate dateTo, Long userId){

        List<Plan> plans = planRepository.findPlansByCompartmentDateAndUserId(dateFrom, dateTo, userId);

        Comparator<Plan> comparator = Comparator
                .comparing((Plan plan) -> plan.getDate())
                .thenComparing((Plan plan) -> plan.getTime());

        Collections.sort(plans, comparator);

        return plans;
    }

    public List<Plan> getPlansByUserId(Long userId){
        List<Plan> plans = planRepository.findPlansByUserId(userId);

        Comparator<Plan> comparator = Comparator
                .comparing((Plan plan) -> plan.getDate())
                .thenComparing((Plan plan) -> plan.getTime());

        Collections.sort(plans, comparator);

        return plans;
    }

    public Optional<Plan> getPlanById(Long id) {
        return planRepository.findById(id);
    }
}
