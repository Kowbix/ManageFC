package com.example.ManageFC.requestClass;

import com.example.ManageFC.enums.PlanType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RequestIndividualPlan {

    private final String name;
    private final String description;
    private final String place;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private final LocalTime time;

    private final List<Long> usersId;

    private final Long teamId;

}
