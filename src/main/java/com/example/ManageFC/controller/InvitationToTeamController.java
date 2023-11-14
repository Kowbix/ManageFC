package com.example.ManageFC.controller;

import com.example.ManageFC.entity.InvitationToTeam;
import com.example.ManageFC.entity.Team;
import com.example.ManageFC.entity.User;
import com.example.ManageFC.service.TeamService;
import com.example.ManageFC.service.InvitationToTeamService;
import com.example.ManageFC.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class InvitationToTeamController {

    private final InvitationToTeamService invitationToTeamService;
    private final TeamService teamService;
    private final UserService userService;

    @PostMapping("/admin/api/v1/addUserToTeam")
    public String inviteUserToTeam(@RequestParam String email, Long id, RedirectAttributes redirectAttributes) {

        User user = userService.findUserByEmail(email);
        Team team = teamService.getTeamById(id);

        if(user == null){
            redirectAttributes.addFlashAttribute("errorMessage", "User not exist");

            return "redirect:/admin/add_user_to_team?id=" + id;
        }

        boolean userGotInvitation = invitationToTeamService.existUserInTeam(team.getId(), user.getId());
        boolean userExistInTeam = teamService.isUserExistInTeam(user.getId(), team.getId());

        if(userGotInvitation || userExistInTeam) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "User got invitation or exist in team");

            return "redirect:/admin/add_user_to_team?id=" + id;
        }

        invitationToTeamService.inviteUserToTeam(new InvitationToTeam(
                user,
                team
        ));

        return "redirect:/myteams";
    }


    @GetMapping("/api/v1/accept_invitation")
    public String acceptInvitationToTeam(@RequestParam Long id, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);

        Optional<InvitationToTeam> invitation = invitationToTeamService.findInvitationById(id);

        if(!invitation.isPresent()) {
            return "redirect:/";
        }

        if(invitation.get().getUser().getId() != user.getId()){
            return "redirect:/";
        }

        invitationToTeamService.acceptInvitation(invitation.get());

        return "redirect:/invitations";
    }

    @GetMapping("/api/v1/delete_invitation")
    public String deleteInvitation(@RequestParam Long id, HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);

        Optional<InvitationToTeam> invitation = invitationToTeamService.findInvitationById(id);

        if(!invitation.isPresent()) {
            return "redirect:/";
        }

        if(invitation.get().getUser().getId() != user.getId()){
            return "redirect:/";
        }

        invitationToTeamService.deleteInvitation(invitation.get());

        return "redirect:/invitations";
    }

    @GetMapping("/invitations")
    public String getInvitation(HttpSession session, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);

        List<InvitationToTeam> invitations = invitationToTeamService.getInvitationByUserId(user.getId());

        model.addAttribute("invitations", invitations);

        return "/team/invitations";

    }

}
