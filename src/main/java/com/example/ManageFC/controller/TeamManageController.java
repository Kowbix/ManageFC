package com.example.ManageFC.controller;


import com.example.ManageFC.entity.Team;
import com.example.ManageFC.entity.User;
import com.example.ManageFC.entity.produckts.Product;
import com.example.ManageFC.entity.produckts.Rental;
import com.example.ManageFC.enums.AccountRole;
import com.example.ManageFC.service.TeamService;
import com.example.ManageFC.service.UserService;
import com.example.ManageFC.service.produckts.ProductService;
import com.example.ManageFC.service.produckts.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class TeamManageController {

    private final UserService userService;
    private final TeamService teamService;

    @GetMapping("/myteams")
    public String adminTeams(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Authentication fail";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);


        Optional<List<Team>> teamsAdmin = teamService.getTeamsByAdmin(user.getId());
        List<Team> teamsMember = teamService.getTeamsByUserId(user.getId());
        teamsMember.removeAll(new HashSet<>(teamsAdmin.get()));

        model.addAttribute("teams", teamsAdmin.get());
        model.addAttribute("teamsMember", teamsMember);

        return "team/admin/myTeams";
    }

    @GetMapping("/admin/add_user_to_team")
    public String inviteUserToGroup(@RequestParam Long id, Model model ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Authentication fail";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);
        Team team = teamService.getTeamById(id);



        if (!team.getTeamAdmin().equals(user)) {
            return "redirect:/myteams";

        }

        model.addAttribute("team", team);

        return "team/admin/addUserToTeamPage";
    }

    @GetMapping("/admin/team-settings")
    public String teamSettingsPage(@RequestParam Long teamId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Authentication fail";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);
        Team team = teamService.getTeamById(teamId);


        if (!team.getTeamAdmin().equals(user)) {
            return "redirect:/myteams";
        }



        model.addAttribute("team", team);

        return "settings/teamSettingsPage";

    }
}
