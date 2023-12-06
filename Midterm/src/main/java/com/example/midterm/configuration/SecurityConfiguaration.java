package com.example.midterm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguaration {

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        AuthenticationManager authenticationManagerBean(UserDetailsService detailsService) throws Exception {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(detailsService);
                return new ProviderManager(provider);
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf((csrf) -> csrf.disable())
                                .authorizeHttpRequests((authorize) -> authorize
                                                .requestMatchers("/", "/category", "/shop", "/brand", "/search",
                                                                "/filter", "/checkout","cart/**","/do-register","/register")
                                                .permitAll()
                                                .requestMatchers("/admin/**").hasAuthority("ADMIN").requestMatchers("/static/**", "/vendors/**","/js/**",
                                                "/css/**","/images/**").permitAll()
                                                .requestMatchers("/cart", "/order","/add-to-cart").authenticated())
                                .formLogin((login) -> login.loginPage("/login")
                                                .loginProcessingUrl("/do-login")
                                                .defaultSuccessUrl("/",true)
                                                .successHandler((request, response, authentication) -> {
                                                        if (authentication.getName().equals("admin")) {
                                                                response.sendRedirect("/admin");
                                                        } else {
                                                                response.sendRedirect("/");
                                                        }
                                                })
                                                .permitAll())
                                .logout(logout->logout.invalidateHttpSession(true)
                                        .clearAuthentication(true)
                                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                        .logoutSuccessUrl("/").permitAll()
                                        )
                                        .sessionManagement(
                                                            session -> session.sessionCreationPolicy(
                                                                        SessionCreationPolicy.IF_REQUIRED))
                                        .build();
        }
}
