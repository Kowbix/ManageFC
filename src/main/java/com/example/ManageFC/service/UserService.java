package com.example.ManageFC.service;

import com.example.ManageFC.entity.User;
import com.example.ManageFC.entity.plans.Plan;
import com.example.ManageFC.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final static  String USER_NOT_FOUND_MSG = "User with %s not found";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public boolean singUpUser(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();

        if (userExists) {
            return false;
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();

//        TODO: SEND EMAIL

        return true;
    }

    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public List<User> getUsersByTeamId(Long teamId) {
        return userRepository.getUsersByTeamId(teamId);
    }

    public List<User> getUsersByIds(List<Long> id) {
        return userRepository.findAllById(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

}
