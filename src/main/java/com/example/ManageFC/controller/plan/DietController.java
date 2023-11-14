package com.example.ManageFC.controller.plan;

import com.example.ManageFC.entity.Meal;
import com.example.ManageFC.entity.Team;
import com.example.ManageFC.entity.User;
import com.example.ManageFC.entity.plans.Diet;
import com.example.ManageFC.enums.MealType;
import com.example.ManageFC.requestClass.DietRequest;
import com.example.ManageFC.service.TeamService;
import com.example.ManageFC.service.UserService;
import com.example.ManageFC.service.plan.DietService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
public class DietController {

    private final DietService dietService;
    private final UserService userService;
    private final TeamService teamService;


    @GetMapping("/admin/add-diet")
    public String showIndividualPlanPage(@RequestParam Long teamId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Error";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);
        Team team = teamService.getTeamById(teamId);

        if(!team.getTeamAdmin().equals(user)){
            return "redirect:/myteams";
        }

        List<User> members = userService.getUsersByTeamId(teamId);

        model.addAttribute("members", members);
        model.addAttribute("team", team);

        return "team/admin/addDietPage";
    }

    @PostMapping("/admin/api/v1/save-diet")
    public String saveIndividualPlan(@ModelAttribute DietRequest diet, Long teamId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Error";
        }
        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);
        Team team = teamService.getTeamById(teamId);

        if(!team.getTeamAdmin().equals(user)){
            return "redirect:/myteams";
        }

        List<User> users = userService.getUsersByIds(diet.getUsersId());

        dietService.addDiet(new Diet(new Meal(diet.getBreakfastName(), diet.getBreakfastUrl(), MealType.BREAKFAST),
                new Meal(diet.getLunchName(), diet.getLunchUrl(), MealType.LUNCH),
                new Meal(diet.getDinerName(), diet.getDinerUrl(), MealType.DINER),
                team, diet.getDate(), users));

        return "redirect:/myteams";
    }
}
