package com.example.ManageFC.controller.plan;

import com.example.ManageFC.entity.Team;
import com.example.ManageFC.entity.User;
import com.example.ManageFC.entity.plans.Plan;
import com.example.ManageFC.enums.PlanType;
import com.example.ManageFC.funkction.IndividualPlanCsvReader;
import com.example.ManageFC.requestClass.RequestDates;
import com.example.ManageFC.requestClass.RequestIndividualPlan;
import com.example.ManageFC.requestClass.RequestPositionPlan;
import com.example.ManageFC.requestClass.RequestTeamPlan;
import com.example.ManageFC.service.TeamService;
import com.example.ManageFC.service.UserService;
import com.example.ManageFC.service.plan.PlanService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class PlanController {

    private final PlanService planService;
    private final UserService userService;
    private final TeamService teamService;
    private final IndividualPlanCsvReader reader;

    @GetMapping("/admin/add-individual-plan")
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

        return "team/admin/addIndividualPlan";
    }

    @PostMapping("/admin/api/v1/save-individual-plan")
    public String saveIndividualPlan(@ModelAttribute @NotNull RequestIndividualPlan individualPlan) {
        List<User> users = userService.getUsersByIds(individualPlan.getUsersId());
        Team team = teamService.getTeamById(individualPlan.getTeamId());

        planService.addPlan(new Plan(individualPlan.getName(), individualPlan.getDescription(), individualPlan.getPlace(),
                individualPlan.getDate(), individualPlan.getTime(), users, team, PlanType.INDIVIDUAL));

        return "redirect:/myteams";
    }

    @GetMapping("/admin/add-team-plan")
    public String showTeamPlanPage(@RequestParam Long teamId, Model model) {
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

        model.addAttribute("team", team);

        return "team/admin/addTeamPlan";
    }

    @PostMapping("/admin/api/v1/save-team-plan")
    public String saveTeamPlan(@ModelAttribute RequestTeamPlan teamPlan){
        List<User> users = userService.getUsersByTeamId(teamPlan.getTeamId());
        Team team = teamService.getTeamById(teamPlan.getTeamId());

        planService.addPlan(new Plan(teamPlan.getName(), teamPlan.getDescription(), teamPlan.getPlace(),
                teamPlan.getDate(), teamPlan.getTime(), users, team, PlanType.TEAM));

        return "redirect:/myteams";
    }

    @GetMapping("/admin/add-position-plan")
    public String showPlanByPositionPage(@RequestParam Long teamId, Model model) {
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

        model.addAttribute("team", team);

        return "team/admin/addPositionPlan";
    }

    @PostMapping("/admin/api/v1/save-position-plan")
    public String savePositionPlan(@ModelAttribute RequestPositionPlan positionPlan) {
        List<User> users = teamService.getUserFromTeamByPosition(positionPlan.getPosition(), positionPlan.getTeamId());
        Team team = teamService.getTeamById(positionPlan.getTeamId());

        planService.addPlan(new Plan(positionPlan.getName(), positionPlan.getDescription(), positionPlan.getPlace(),
                positionPlan.getDate(), positionPlan.getTime(), users, team, PlanType.POSITION));

        return "redirect:/myteams";
    }

    @GetMapping("/team/plan-info")
    public String getInfoAboutPlan(@RequestParam Long planId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated() || authentication.getName().equals("anonymousUser")) {
            return "Error";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);

        Optional<Plan> plan = planService.getPlanById(planId);

        if (!plan.get().getUsers().contains(user)){
            return "redirect:/";
        }

        model.addAttribute("plan", plan.get());

        return "team/planInfoPage";
    }

    @GetMapping("/admin/add-individual-plan-csv")
    public String showIndividualPlanCSVPage(@RequestParam Long teamId, Model model){
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

        return "team/admin/addIndividualPlanCSV";
    }

    @PostMapping("/admin/api/v1/save-individual-plan-csv")
    public String saveIndividualPlanCSV(@RequestParam("file") MultipartFile file, Long teamId){
        Team team = teamService.getTeamById(teamId);

//        reader.readCSVFile(file, team);

        if (!reader.isCSVFile(file)){
            reader.readCSVFile(file, team);
        }

        return "redirect:/myteams";
    }

}
