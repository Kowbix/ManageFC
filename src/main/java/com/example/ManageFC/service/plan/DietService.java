package com.example.ManageFC.service.plan;

import com.example.ManageFC.entity.Meal;
import com.example.ManageFC.entity.plans.Diet;
import com.example.ManageFC.repository.plan.DietRepository;
import com.example.ManageFC.service.MealService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class DietService {

    private final DietRepository dietRepository;
    private final MealService mealService;

    public Diet addDiet(Diet diet) {
        Meal breakfast = mealService.addMeal(diet.getBreakfast());
        Meal lunch = mealService.addMeal(diet.getLunch());
        Meal diner = mealService.addMeal(diet.getDiner());

        return dietRepository.save(diet);
    }


    public List<Diet> getDietByUserId(Long id) {
        return dietRepository.findDietByUserId(id);
    }

    public List<Diet> getDietByDateAndUserId(LocalDate date, Long id) {
        return dietRepository.findDietByDateAndUseId(date, id);
    }

    public List<Diet> getDietsByCompartmentDateAndUserId(LocalDate dateFrom, LocalDate dateTo, Long id) {
        List<Diet> diets = dietRepository.findDietsByCompartmentDateAndUserId(dateFrom, dateTo, id);

        Comparator<Diet> comparator = Comparator
                .comparing((Diet d) -> d.getDate());

        Collections.sort(diets,comparator);

        return diets;
    }
}
