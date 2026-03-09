package com.ets.auth;




import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ets.enums.Role;
import com.ets.model.AdminLoginUser;
import com.ets.repository.AdminLoginUserRepository;

@Configuration
public class AdminLoginSeeder {

    @Bean
    CommandLineRunner seedAdmin(AdminLoginUserRepository repo, PasswordEncoder encoder) {
        return args -> {

            repo.findByUsername("admin").orElseGet(() -> {

                AdminLoginUser admin = new AdminLoginUser();

                admin.setUsername("admin");
                admin.setEmail("admin@gmail.com");
                admin.setPasswordHash(encoder.encode("admin@123"));
                admin.setRole(Role.ADMIN);
                admin.setEnabled(true);

                return repo.save(admin);
            });

        };
    }
}
