package com.example.ManageFC.repository;

import com.example.ManageFC.entity.Team;
import com.example.ManageFC.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    User findUserByEmail(String email);

    User findUserById(Long userId);

    @Query("SELECT u FROM User u JOIN u.teams t WHERE t.id = :teamId")
    List<User> getUsersByTeamId(Long teamId);

}
