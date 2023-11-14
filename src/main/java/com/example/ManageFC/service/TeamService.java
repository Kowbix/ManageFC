package com.example.ManageFC.service;

import com.example.ManageFC.entity.Team;
import com.example.ManageFC.entity.User;
import com.example.ManageFC.repository.TeamRepository;
import com.example.ManageFC.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    public void deleteTeam(Team team) {
        teamRepository.deleteById(team.getId());
    }

    public Team getTeamById(Long teamId) {
        return teamRepository.findTeamById(teamId);
    }

    public Optional<List<Team>> getTeamsByAdmin(Long adminId) {
        return teamRepository.findTeamByTeamAdminId(adminId);
    }

    public boolean isUserExistInTeam(Long userId, Long teamId) {
         List<Team> teams = teamRepository.findTeamsByUserId(userId);

         return teams.stream().anyMatch(t -> t.getId().equals(teamId));

    }

    public void addUserToTeam(Long userId, Long teamId){
        User user = userRepository.findUserById(userId);
        Team team = teamRepository.findTeamById(teamId);

        user.getTeams().add(team);
        team.getUsers().add(user);

        userRepository.save(user);
    }

    public void deleteUserFromTeam(Long userId, Team team) {

        User user = userRepository.findUserById(userId);

        team.getUsers().remove(user);
        user.getTeams().remove(team);

        teamRepository.save(team);
        userRepository.save(user);
    }

    public List<Team> getTeamsByUserId(Long userId){
        return teamRepository.findTeamsByUserId(userId);
    }

    public List<User> getUserFromTeamByPosition(String position, Long teamId) {
        return teamRepository.findUsersByPosition(position, teamId);
    }
}
