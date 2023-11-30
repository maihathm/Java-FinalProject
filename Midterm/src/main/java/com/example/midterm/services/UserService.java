package com.example.midterm.services;

import com.example.midterm.model.User;
import com.example.midterm.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(email).orElseThrow();
    };

    public boolean checkUserExistsByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    };
}
