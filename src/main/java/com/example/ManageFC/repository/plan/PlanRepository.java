package com.example.ManageFC.repository.plan;

import com.example.ManageFC.entity.plans.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Query("SELECT p FROM Plan p JOIN p.users u WHERE p.date= :date AND p.time>= :time AND u.id= :userId")
    List<Plan> findPlansByDateAndUserId(LocalDate date, Long userId, LocalTime time);

    @Query("SELECT p FROM Plan p JOIN p.users u WHERE p.date>= :dateFrom AND p.date< :dateTo AND u.id= :userId")
    List<Plan> findPlansByCompartmentDateAndUserId(LocalDate dateFrom, LocalDate dateTo, Long userId);

    @Query("SELECT p FROM Plan p JOIN p.users u WHERE u.id= :userId")
    List<Plan> findPlansByUserId(Long userId);
}
