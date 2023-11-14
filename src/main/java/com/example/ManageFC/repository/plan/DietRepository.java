package com.example.ManageFC.repository.plan;

import com.example.ManageFC.entity.plans.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DietRepository extends JpaRepository<Diet, Long> {

    @Query("SELECT d FROM Diet d JOIN d.users u WHERE u.id= :userId")
    List<Diet> findDietByUserId(Long userId);

    @Query("SELECT d FROM Diet d JOIN d.users u WHERE d.date= :date AND u.id= :userId")
    List<Diet> findDietByDateAndUseId(LocalDate date, Long userId);

    @Query("SELECT d FROM Diet d JOIN d.users u WHERE d.date>= :dateFrom AND d.date< :dateTo AND u.id= :userId")
    List<Diet> findDietsByCompartmentDateAndUserId(LocalDate dateFrom, LocalDate dateTo, Long userId);
}
