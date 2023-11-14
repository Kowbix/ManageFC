package com.example.ManageFC.repository;

import com.example.ManageFC.entity.InvitationToTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationToTeamRepository extends JpaRepository<InvitationToTeam, Long> {

    boolean existsByTeamIdAndUserId(Long teamId, Long userId);
    List<InvitationToTeam> findByUserId(Long userId);

}
