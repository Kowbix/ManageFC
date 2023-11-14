package com.example.ManageFC.repository;

import com.example.ManageFC.entity.Team;
import com.example.ManageFC.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<List<Team>> findTeamByTeamAdminId(Long adminId);
    Team findTeamById(Long id);

    @Query("SELECT t FROM Team t JOIN t.users u WHERE u.id = :userId")
    List<Team> findTeamsByUserId(Long userId);

    @Query("SELECT u FROM Team t JOIN t.users u WHERE t.id= :teamId AND u.position= :position ")
    List<User> findUsersByPosition(String position, Long teamId);
}
