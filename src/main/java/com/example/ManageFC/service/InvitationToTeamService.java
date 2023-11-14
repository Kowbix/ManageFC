package com.example.ManageFC.service;

import com.example.ManageFC.entity.InvitationToTeam;
import com.example.ManageFC.repository.InvitationToTeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InvitationToTeamService {

    private final InvitationToTeamRepository invitationToTeamRepository;
    private final TeamService teamService;

    public InvitationToTeam inviteUserToTeam(InvitationToTeam invitationToTeam) {
        return invitationToTeamRepository.save(invitationToTeam);
    }

    public boolean existUserInTeam(Long teamId, Long userId){
        return invitationToTeamRepository.existsByTeamIdAndUserId(teamId, userId);
    }

    public Optional<InvitationToTeam> findInvitationById(Long id) {
        return invitationToTeamRepository.findById(id);
    }

    public boolean acceptInvitation(InvitationToTeam invitation) {

        teamService.addUserToTeam(invitation.getUser().getId(),
                invitation.getTeam().getId());

        deleteInvitation(invitation);

        return true;
    }

    public void deleteInvitation(InvitationToTeam invitation) {
        invitationToTeamRepository.delete(invitation);
    }

    public List<InvitationToTeam> getInvitationByUserId(Long userId) {
        return invitationToTeamRepository.findByUserId(userId);
    }

}
