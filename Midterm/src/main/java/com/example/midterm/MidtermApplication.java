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
    CommandLineRunner demo() {
        return (args) -> {
            System.out.println("Web Application is running...");
        };
    }
}
