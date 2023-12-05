package com.example.midterm.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.midterm.dto.UserDTO;
import com.example.midterm.model.Role;
import com.example.midterm.model.User;
import com.example.midterm.services.RoleService;
import com.example.midterm.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final RoleService roleService;

    private final BCryptPasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Username or password invalid!");
        }
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/do-register")
    public String registerCustomer( @ModelAttribute("userDTO") UserDTO userDTO,
                                   BindingResult result,
                                   Model model) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("userDTO",new UserDTO());
                return "register";
            } else {
                String username = userDTO.getUsername();
                User checkExist = userService.findByUsername(username);
                if (checkExist != null) {
                    model.addAttribute("error", "Username already exists!");
                    return "register";
                }


                userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                userDTO.setEmail(userDTO.getEmail());
                User createdUser = userService.save(userDTO);
                System.out.println("asdasdasdasd");
                if (createdUser != null) {
                    Role customerRole = new Role();
                    customerRole.setAuthority("USER");
                    roleService.save(customerRole);

                    Set<Role> updatedRoles = new HashSet<>(createdUser.getRoles());
                    updatedRoles.add(customerRole);
                    createdUser.setRoles(updatedRoles);
                    userService.save(createdUser);
                }
                model.addAttribute("success", "Register successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Server is error, try again later!");
        }
        return "login";
    }
}
