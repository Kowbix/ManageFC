package com.example.ManageFC.entity.produckts;

import com.example.ManageFC.entity.Team;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Boolean isActive;

    @ManyToOne
    private Team team;

    @OneToMany(mappedBy = "product")
    private Set<Rental> rental;


    public Product(String name, Team team) {
        this.name = name;
        this.team = team;
        this.isActive = true;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                ", team=" + team +
                '}';
    }
}
