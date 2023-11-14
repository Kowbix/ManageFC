package com.example.ManageFC.controller;

import com.example.ManageFC.entity.User;
import com.example.ManageFC.requestClass.UserUpdateRequest;
import com.example.ManageFC.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@AllArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    private HttpSession httpSession;

    @PostMapping("/api/v1/update-profile")
    public String updateUser(@ModelAttribute UserUpdateRequest userRequest, Long userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication.getName().equals("anonymousUser")) {
            return "redirect:/";
        }

        String userEmail = authentication.getName();
        User authenticatedUser = userService.findUserByEmail(userEmail);
        User userToUpdate = userService.getUserById(userId);

        if (!authenticatedUser.equals(userToUpdate)) {
            return "redirect:/";
        }

        userToUpdate.setFirstName(userRequest.getFirstName());
        userToUpdate.setLastName(userRequest.getLastName());
        userToUpdate.setBirthDate(userRequest.getBirthDate());
        userToUpdate.setPosition(userRequest.getPosition());

        userService.updateUser(userToUpdate);

        return "redirect:/";
    }
}
