package com.example.midterm.services;

import com.example.midterm.dto.UserDTO;
import com.example.midterm.model.Role;
import com.example.midterm.model.User;
import com.example.midterm.repos.RoleRepository;
import com.example.midterm.repos.UserRepository;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User getUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(email).orElseThrow();
    };

    public boolean checkUserExistsByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    };

    // save user
    public User save(UserDTO user) {
        User newUser = new User();
        newUser.setPassword(user.getPassword());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        return userRepository.save(newUser);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }



}
