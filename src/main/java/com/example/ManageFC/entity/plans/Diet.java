package com.example.ManageFC.entity.plans;

import com.example.ManageFC.entity.Meal;
import com.example.ManageFC.entity.Team;
import com.example.ManageFC.entity.User;
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
public class Diet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private Meal breakfast;

    @OneToOne
    private Meal lunch;

    @OneToOne
    private Meal diner;

    @OneToOne
    private Team team;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @ManyToMany
    private List<User> users = new ArrayList<>();

    public Diet(Meal breakfast, Meal lunch, Meal diner, Team team, LocalDate date, List<User> users) {
        this.breakfast = breakfast;
        this.breakfast.setEndTime(LocalTime.of(10, 00));
        this.breakfast.setStartTime(LocalTime.of(8, 00));

        this.lunch = lunch;
        this.lunch.setEndTime(LocalTime.of(15, 00));
        this.lunch.setStartTime(LocalTime.of(13, 00));

        this.diner = diner;
        this.diner.setEndTime(LocalTime.of(20, 00));
        this.diner.setStartTime(LocalTime.of(18, 00));

        this.team = team;
        this.date = date;
        this.users = users;
    }
}
