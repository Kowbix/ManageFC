package com.example.ManageFC.controller;

import com.example.ManageFC.requestClass.RegistrationUserRequest;
import com.example.ManageFC.service.RegistrationUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
@AllArgsConstructor
public class RegistrationUserController {

    private RegistrationUserService registrationUserService;

    @PostMapping(path = "/api/v1/registration")
    public String registerUser(@ModelAttribute RegistrationUserRequest request, RedirectAttributes redirectAttributes) {
        boolean isRegistered = registrationUserService.registerUser(request);

        if(!isRegistered) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email already taken");
            return "redirect:/registration";
        }

        return "redirect:/login";
    }

    @PostMapping(path = "/admin/api/v1/registration")
    public String registerAdmin(@ModelAttribute RegistrationUserRequest request, RedirectAttributes redirectAttributes){
        boolean isRegistered = registrationUserService.registerAdmin(request);

        if(!isRegistered) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email already taken");
            return "redirect:/admin/registration";
        }

        return "redirect:/myteams";
    }

    @GetMapping("/registration")
    public String userRegistration() {
        return "registration/userRegistration";
    }

    @GetMapping("/admin/registration")
    public String adminRegistration() {
        return "registration/adminRegistration";
    }
}
