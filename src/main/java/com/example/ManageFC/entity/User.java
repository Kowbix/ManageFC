package com.example.ManageFC.entity;

import com.example.ManageFC.entity.plans.Diet;
import com.example.ManageFC.entity.plans.Plan;
import com.example.ManageFC.entity.produckts.Rental;
import com.example.ManageFC.enums.AccountRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Getter
@Setter
//@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String position;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    private Boolean locked = false;
    private Boolean enabled = true;

    @ManyToMany(mappedBy = "users")
    private Set<Team> teams;

    @ManyToMany(mappedBy = "users")
    private Set<Plan> plans;

    @ManyToMany(mappedBy = "users")
    private Set<Diet> diets;

    @OneToMany(mappedBy = "user")
    private Set<Rental> rentals;

    public User(String firstName, String lastName,
                String email, String password, String position,
                LocalDate birthDate, AccountRole accountRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.position = position;
        this.birthDate = birthDate;
        this.accountRole = accountRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + accountRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
