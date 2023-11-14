package com.example.ManageFC.service;

import com.example.ManageFC.entity.User;
import com.example.ManageFC.enums.AccountRole;
import com.example.ManageFC.funkction.EmailValidator;
import com.example.ManageFC.funkction.TextChanger;
import com.example.ManageFC.requestClass.RegistrationUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationUserService {

    private final UserService userService;
    private EmailValidator emailValidator;
    private TextChanger textChanger;

    public boolean registerUser(RegistrationUserRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if(!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        return userService.singUpUser(new User(
                textChanger.toUpperCase(request.getFirstName()),
                textChanger.toUpperCase(request.getLastName()),
                request.getEmail(),
                request.getPassword(),
                request.getPosition(),
                request.getBirthDate(),
                AccountRole.USER
        ));
    }


    public boolean registerAdmin(RegistrationUserRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if(!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        return userService.singUpUser(new User(
                textChanger.toUpperCase(request.getFirstName()),
                textChanger.toUpperCase(request.getLastName()),
                request.getEmail(),
                request.getPassword(),
                "Staff",
                request.getBirthDate(),
                AccountRole.ADMIN
        ));
    }
}
