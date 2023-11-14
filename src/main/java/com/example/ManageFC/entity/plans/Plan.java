package com.example.ManageFC.entity.plans;

import com.example.ManageFC.entity.Team;
import com.example.ManageFC.entity.User;
import com.example.ManageFC.enums.PlanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String place;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime time;

    @ManyToMany
    private List<User> users = new ArrayList<>();

    @ManyToOne
    private Team team;

    private PlanType planType;

    public Plan(String name, String description, String place,
                LocalDate date, LocalTime time, List<User> users, Team team, PlanType planType) {
        this.name = name;
        this.description = description;
        this.place = place;
        this.date = date;
        this.time = time;
        this.users = users;
        this.team = team;
        this.planType = planType;
    }
}
