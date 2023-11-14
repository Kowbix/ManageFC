package com.example.ManageFC.entity;

import com.example.ManageFC.entity.produckts.Product;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_admin_id")
    private User teamAdmin;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();

    private String teamName;
    private LocalDate creationDate;

    @OneToMany(mappedBy = "team")
    private Set<Product> products;


    public Team(User teamAdmin, String teamName, LocalDate creationDate) {
        this.teamAdmin = teamAdmin;
        this.teamName = teamName;
        this.creationDate = creationDate;
    }


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public User getTeamAdmin() {
        return teamAdmin;
    }

    public void setTeamAdmin(User teamAdmin) {
        this.teamAdmin = teamAdmin;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
