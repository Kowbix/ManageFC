package com.example.ManageFC.requestClass;

import com.example.ManageFC.entity.Meal;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DietRequest {

    private final String breakfastName;
    private final String breakfastUrl;

    private final String lunchName;
    private final String lunchUrl;

    private final String dinerName;
    private final String dinerUrl;


    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate date;

    private final List<Long> usersId;


}
