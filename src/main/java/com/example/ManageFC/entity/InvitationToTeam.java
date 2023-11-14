package com.example.ManageFC.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class InvitationToTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    @ManyToOne
    private Team team;

    private LocalDateTime invitationDate;

    public InvitationToTeam(User user, Team team) {
        this.team = team;
        this.user = user;
        this.invitationDate = LocalDateTime.now();
    }
}
