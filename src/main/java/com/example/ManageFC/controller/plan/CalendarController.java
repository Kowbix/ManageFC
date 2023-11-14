package com.example.ManageFC.controller.plan;

import com.example.ManageFC.entity.User;
import com.example.ManageFC.entity.plans.Diet;
import com.example.ManageFC.entity.plans.Plan;
import com.example.ManageFC.entity.produckts.Rental;
import com.example.ManageFC.requestClass.RequestDates;
import com.example.ManageFC.service.UserService;
import com.example.ManageFC.service.plan.DietService;
import com.example.ManageFC.service.plan.PlanService;
import com.example.ManageFC.service.produckts.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class CalendarController {

    private final PlanService planService;
    private final UserService userService;
    private final DietService dietService;
    private final RentalService rentalService;

    @GetMapping("/find-plans-by-date")
    public String getAddDatesToCalendarPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getName().equals("anonymousUser")) {
            return "/mainPage";
        }

        return "team/addDatesToCalendarPage";
    }

    @PostMapping("/my-calendar")
    public String getUserCalendar(@ModelAttribute RequestDates dates, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Error";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);

        List<Plan> plans = planService.getPlansByCompartmentDateAndUserId(dates.getDateFrom(),
                dates.getDateTo(), user.getId());

        List<Diet> diet = dietService.getDietsByCompartmentDateAndUserId(dates.getDateFrom(),
                dates.getDateTo(), user.getId());

        List<Rental> rental = rentalService.getRentalByCompartmentDateAndUserId(dates.getDateFrom(),
                dates.getDateTo(), user.getId());

        model.addAttribute("plans", plans);
        model.addAttribute("diet", diet);
        model.addAttribute("rental", rental);
        model.addAttribute("from", dates.getDateFrom());
        model.addAttribute("to", dates.getDateTo());

        return "team/calendarPage";
    }

    @GetMapping("/all-user-plans")
    public String getAllPlansByUserId(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Error";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);

        List<Plan> plans = planService.getPlansByUserId(user.getId());
        List<Diet> diet = dietService.getDietByUserId(user.getId());
        List<Rental> rental = rentalService.getRentalByUserId(user.getId());

        model.addAttribute("plans", plans);
        model.addAttribute("diet", diet);
        model.addAttribute("rental", rental);
        model.addAttribute("user", user);

        return "team/allUserPlansPage";
    }

    @PostMapping()
    public String deleteRental(){
//        TODO: ADD FUNCTION TO DELETE RENT
        return "";
    }
}
