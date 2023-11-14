package com.example.ManageFC.controller.produckts;

import com.example.ManageFC.entity.User;
import com.example.ManageFC.entity.produckts.Product;
import com.example.ManageFC.entity.produckts.Rental;
import com.example.ManageFC.service.TeamService;
import com.example.ManageFC.service.UserService;
import com.example.ManageFC.service.produckts.ProductService;
import com.example.ManageFC.service.produckts.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class RentalController {

    private final RentalService rentalService;
    private final ProductService productService;
    private final UserService userService;
    private final TeamService teamService;

    @GetMapping("/rent-product")
    public String rentalPage(@RequestParam Long productId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Error";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);
        Product product = productService.getProductById(productId);

        if(!teamService.isUserExistInTeam(user.getId(), product.getTeam().getId())) {
            return "redirect:/myteams";
        }

        model.addAttribute("product", product);

        return "team/rentProductPage";
    }

    @PostMapping("/api/v1/rent-product")
    public String rentProduct(@RequestParam Long productId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                              RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Error";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);
        Product product = productService.getProductById(productId);

        if(!teamService.isUserExistInTeam(user.getId(), product.getTeam().getId())) {
            return "redirect:/myteams";
        }

        if(LocalDate.now().isAfter(date)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Wrong date");
            return "redirect:/rent-product?productId=" + productId;
        }

        if(rentalService.isProductOccupied(productId, date)){
            redirectAttributes.addFlashAttribute("errorMessage",
                    "The product is occupied that day, choose other day");
            return "redirect:/rent-product?productId=" + productId;
        }
        rentalService.rentProduct(new Rental(product, date, user));

        return "redirect:/myteams";
    }

    @GetMapping("/api/v1/cancel-rental")
    public String cancelRental(@RequestParam Long rentalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Error";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);
        Optional<Rental> rental = rentalService.getRentalById(rentalId);

        if(rental.isEmpty()){
            return "redirect:/myteams";
        } else if (!rental.get().getUser().equals(user)) {
            return "redirect:/myteams";
        }

        rentalService.cancelRental(rental.get());

        return "redirect:/myteams";
    }
}
