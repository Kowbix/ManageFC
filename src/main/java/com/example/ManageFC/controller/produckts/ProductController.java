package com.example.ManageFC.controller.produckts;

import com.example.ManageFC.entity.Team;
import com.example.ManageFC.entity.User;
import com.example.ManageFC.entity.produckts.Product;
import com.example.ManageFC.service.TeamService;
import com.example.ManageFC.service.UserService;
import com.example.ManageFC.service.produckts.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@AllArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final TeamService teamService;

    @GetMapping("/admin/add-product")
    public String addNewProductPage(@RequestParam Long teamId, Model model) {

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

        return "team/admin/addNewProductPage";
    }

    @PostMapping("/admin/api/v1/save-product")
    public String saveNewProduct(@RequestParam Long teamId, String productName) {

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

        productService.saveProduct(new Product(productName, team));

        return "redirect:/myteams";
    }

    @GetMapping("/team/products")
    public String getActiveProductByTeamIdPage(@RequestParam Long teamId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Error";
        }

        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);
        Team team = teamService.getTeamById(teamId);

        boolean userExistInTeam = teamService.isUserExistInTeam(user.getId(), teamId);

        if(!userExistInTeam){
            return "redirect:/";
        }

        List<Product> activeProducts = productService.getActiveProductsByTeamId(teamId);

        if(team.getTeamAdmin().equals(user)){
            List<Product> inactivateProducts = productService.getInactivateProducts(teamId);
            model.addAttribute("inactivateProducts", inactivateProducts);
        }

        model.addAttribute("products", activeProducts);
        model.addAttribute("team", team);

        return "team/productsInTeam";
    }

    @GetMapping("/admin/api/v1/deactivate-product")
    public String deactivateProduct(@RequestParam Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Error";
        }
        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);
        Product product = productService.getProductById(productId);

        if(!product.getTeam().getTeamAdmin().equals(user)){
            return "redirect:/";
        }

        product.setIsActive(false);
        productService.saveProduct(product);

        return "redirect:/myteams";
    }

    @GetMapping("/admin/api/v1/activate-product")
    public String activeProduct(@RequestParam Long productId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null && !authentication.isAuthenticated()) {
            return "Error";
        }
        String userEmail = authentication.getName();
        User user = userService.findUserByEmail(userEmail);
        Product product = productService.getProductById(productId);

        if(!product.getTeam().getTeamAdmin().equals(user)){
            return "redirect:/";
        }

        product.setIsActive(true);

        productService.saveProduct(product);

        return "redirect:/myteams";
    }


}
