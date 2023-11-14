package com.example.ManageFC.controller;

import com.example.ManageFC.entity.Team;
import com.example.ManageFC.entity.User;
import com.example.ManageFC.enums.AccountRole;
import com.example.ManageFC.requestClass.TeamRequest;
import com.example.ManageFC.service.TeamService;
import com.example.ManageFC.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Controller
public class TeamController {

    UserService userService;
    TeamService teamService;

    @PostMapping("/admin/api/v1/addteam")
    public String addTeamAPI(@ModelAttribute TeamRequest team) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Team didn't add";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);

        Team newTeam = teamService.addTeam(new Team(
                user,
                team.getTeamName(),
                LocalDate.now()
        ));

        teamService.addUserToTeam(user.getId(), newTeam.getId());

        return "redirect:/myteams";
    }

    @GetMapping("/admin/addteam")
    public String addTeam() {

        return "team/addTeamPage";
    }

    @GetMapping("/team/members")
    public String getTeamMembers(@RequestParam Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Error";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);
        Team team = teamService.getTeamById(id);

        boolean userExistInTeam = teamService.isUserExistInTeam(user.getId(), id);

        if(!userExistInTeam){
            if(user.getAccountRole() == AccountRole.ADMIN) {
                return "redirect:/myteams";
            } else if (user.getAccountRole() == AccountRole.USER) {
                return "redirect:/myteams";
            } else {
                return "redirect:/";
            }
        }

        List<User> members = userService.getUsersByTeamId(id);

        model.addAttribute("team", team);
        model.addAttribute("members", members);

        return "team/members";
    }


    @GetMapping("/admin/api/v1/remove_user")
    public String removeUserFromTeam(@RequestParam Long teamId, Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Error";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);
        Team team = teamService.getTeamById(teamId);

        if(!team.getTeamAdmin().equals(user)) {
            return "redirect:/";
        }

        teamService.deleteUserFromTeam(userId, team);

        return "redirect:/myteams";
    }



}
