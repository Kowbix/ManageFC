package com.example.ManageFC.controller;

import com.example.ManageFC.entity.User;
import com.example.ManageFC.entity.plans.Diet;
import com.example.ManageFC.entity.plans.Plan;
import com.example.ManageFC.entity.produckts.Product;
import com.example.ManageFC.entity.produckts.Rental;
import com.example.ManageFC.service.TeamService;
import com.example.ManageFC.service.UserService;
import com.example.ManageFC.service.plan.DietService;
import com.example.ManageFC.service.plan.PlanService;
import com.example.ManageFC.service.produckts.ProductService;
import com.example.ManageFC.service.produckts.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class PageController {

    private final UserService userService;
    private final PlanService planService;
    private final DietService dietService;
    private final RentalService rentalService;


    @GetMapping("/")
    public String mainPage(HttpSession session, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getName().equals("anonymousUser")) {
            return "/mainPage";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);
        LocalDate currentDate = LocalDate.now();

        List<Plan> plans = planService.getPlanByDateAndUserId(currentDate, user.getId(), LocalTime.now());
        List<Diet> diet = dietService.getDietByDateAndUserId(currentDate, user.getId());
        List<Rental> rental = rentalService.getRentByUserAndDate(currentDate, user.getId());

        model.addAttribute("plans", plans);
        model.addAttribute("diet", diet);
        model.addAttribute("rental", rental);
        model.addAttribute("currentDate", currentDate);

        return "/mainPage";
    }

    @GetMapping("/edit-profile")
    public String getEditProfilePage(@RequestParam Long userId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getName().equals("anonymousUser")) {
            return "redirect:/";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);

        User editingUser = userService.getUserById(userId);

        if(!user.equals(editingUser)) {
            return "redirect:/";
        }

        model.addAttribute("user", editingUser);

        return "/team/editProfilePage";

    }

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {

        if(error != null) {
            model.addAttribute("errorMessage", "Incorrect data");
        }

        return "loginPage";
    }

}
