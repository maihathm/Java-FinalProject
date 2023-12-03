package com.example.midterm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.midterm.services.ProductService;

@SpringBootApplication
public class MidtermApplication {

    public static void main(String[] args) {
        SpringApplication.run(MidtermApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(ProductService productService) {
        return (args) -> {
            System.out.println("Web Application is running...");
            // String rawPassword = "123"; // Mật khẩu nguyên bản cần mã hóa
            // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            // String encodedPassword = passwordEncoder.encode(rawPassword); // Mã hóa mật khẩu

            // System.out.println("Mật khẩu nguyên bản: " + rawPassword);
            // System.out.println("Mật khẩu đã mã hóa: " + encodedPassword);
        };
    }
}
