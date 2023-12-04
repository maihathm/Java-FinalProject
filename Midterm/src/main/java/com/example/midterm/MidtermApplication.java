package com.example.midterm;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.midterm.model.Role;
import com.example.midterm.model.User;
import com.example.midterm.repos.RoleRepository;
import com.example.midterm.repos.UserRepository;

@SpringBootApplication
public class MidtermApplication {

    public static void main(String[] args) {
        SpringApplication.run(MidtermApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return (args) -> {
            System.out.println("Web Application is running...");
            // Role adminRole = roleRepository.save(new Role("ADMIN"));
            // Role userRole = roleRepository.save(new Role("USER"));
            // Set<Role> adminRoles = new HashSet<>();
            // adminRoles.add(adminRole);
            // Set<Role> userRoles = new HashSet<>();
            // userRoles.add(userRole);
            // User admin = new User("admin@example.com", "admin", passwordEncoder.encode("123"), adminRoles);
            // User user = new User("user@example.com", "user", passwordEncoder.encode("123"), userRoles);
            // userRepository.save(admin);
            // userRepository.save(user);
        };
    }
}
