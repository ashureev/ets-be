package com.ets.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Basic Auth users (only for testing)
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .roles("ADMIN")
                        .build(),
                User.builder()
                        .username("employee")
                        .password(passwordEncoder.encode("emp123"))
                        .roles("EMPLOYEE")
                        .build()
        );
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           DaoAuthenticationProvider authenticationProvider) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.authenticationProvider(authenticationProvider);

        http.authorizeHttpRequests(auth -> auth
                // ✅ PUBLIC
                .requestMatchers(
                        "/api/admin/login",
                        "/api/employee/login",
                        "/api/employees/create",
                        "/api/employees/all",
                        "/api/employees/delete/**",
                        "/error"
                ).permitAll()
                
                .requestMatchers(
                        "/error",

                        "/api/admin/login",
                        "/api/admin/forgot-password",
                        "/api/admin/reset-password",

                        "/api/employee/login"
//                         add employee forgot/reset here if you have them
//                         "/api/employee/forgot-password",
//                         "/api/employee/reset-password"
                        
                ).permitAll()
                .requestMatchers("/api/admin/salary-management/**").permitAll()
                .requestMatchers("/employee-salary-management/**").permitAll()
                .requestMatchers("/api/notifications/**").permitAll()
                .requestMatchers("/api/reports/**").permitAll()
                .requestMatchers("/api/users/**").permitAll()
                .requestMatchers("/admin/tasks", "/admin/**").permitAll()


                // ✅ ROLE BASED
                .requestMatchers("/api/challenges/**").permitAll()
                .requestMatchers("/api/employee/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/employee/**").hasRole("EMPLOYEE")
                .requestMatchers("/api/tasks/**").hasAnyRole("ADMIN", "EMPLOYEE")
                .requestMatchers("/api/admin/login", "/error").permitAll()
                

                .anyRequest().authenticated()
        );

        http.httpBasic(Customizer.withDefaults());
        http.formLogin(form -> form.disable());

        return http.build();
    }
}

