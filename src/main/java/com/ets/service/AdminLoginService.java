package com.ets.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ets.model.AdminLoginUser;
import com.ets.repository.AdminLoginUserRepository;

@Service
public class AdminLoginService {

    private final AdminLoginUserRepository adminLoginUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    // token -> email
    private final ConcurrentMap<String, String> resetTokenStore = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, LocalDateTime> tokenExpiryStore = new ConcurrentHashMap<>();

    public AdminLoginService(AdminLoginUserRepository adminLoginUserRepository,
                             PasswordEncoder passwordEncoder,
                             JavaMailSender mailSender) {
        this.adminLoginUserRepository = adminLoginUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    // LOGIN
    public String adminLogin(String usernameOrEmail, String password) {
        AdminLoginUser user = findUserByUsernameOrEmail(usernameOrEmail);

        if (!user.isEnabled()) {
            throw new RuntimeException("User account is disabled");
        }

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        return "Welcome " + user.getUsername() + " (" + user.getRole().name() + ")";
    }

    // FORGOT PASSWORD
    public String forgotPassword(String usernameOrEmail) {
        AdminLoginUser user = findUserByUsernameOrEmail(usernameOrEmail);

        String token = UUID.randomUUID().toString();
        resetTokenStore.put(token, user.getEmail());
        tokenExpiryStore.put(token, LocalDateTime.now().plusMinutes(15));

        String resetLink = "http://localhost:3000/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Reset Password");
        message.setText(
            "Hello " + user.getUsername() + ",\n\n" +
            "Click the link below to reset your password:\n" +
            resetLink + "\n\n" +
            "This link will expire in 15 minutes."
        );

        mailSender.send(message);

        return "Password reset link sent to registered email";
    }

    // RESET PASSWORD
    public String resetPassword(String token, String newPassword) {
        if (!resetTokenStore.containsKey(token)) {
            throw new RuntimeException("Invalid reset token");
        }

        LocalDateTime expiry = tokenExpiryStore.get(token);
        if (expiry == null || LocalDateTime.now().isAfter(expiry)) {
            resetTokenStore.remove(token);
            tokenExpiryStore.remove(token);
            throw new RuntimeException("Reset token expired");
        }

        String email = resetTokenStore.get(token);

        AdminLoginUser user = adminLoginUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        adminLoginUserRepository.save(user);

        resetTokenStore.remove(token);
        tokenExpiryStore.remove(token);

        return "Password reset successfully";
    }

    // COMMON METHOD
    private AdminLoginUser findUserByUsernameOrEmail(String usernameOrEmail) {
        Optional<AdminLoginUser> optionalUser = adminLoginUserRepository.findByUsername(usernameOrEmail);

        if (optionalUser.isEmpty()) {
            optionalUser = adminLoginUserRepository.findByEmail(usernameOrEmail);
        }

        return optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
    }
}