package com.example.ManageFC.service;

import com.example.ManageFC.entity.Meal;
import com.example.ManageFC.repository.MealRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MealService {

    private final MealRepository mealRepository;

    public Meal addMeal(Meal meal) {
        return mealRepository.save(meal);
    }
}
