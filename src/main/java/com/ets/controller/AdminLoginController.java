package com.ets.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ets.auth.JwtService;
import com.ets.model.AdminLoginUser;
import com.ets.service.AdminLoginService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminLoginController {

    private final AdminLoginService service;
    private final JwtService jwtService;

    public AdminLoginController(AdminLoginService service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            if (username == null) username = body.get("email");
            if (username == null) username = body.get("emailAddress");
            
            String password = body.get("password");

            if (username == null || username.isBlank() || password == null || password.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "Username/Email or password missing in request"));
            }

            AdminLoginUser user = service.adminLogin(username, password);

            String role = user.getRole().name();
            String token = jwtService.createToken(user.getUsername(), role);

            return ResponseEntity.ok(Map.of(
                    "role", role,
                    "user", user.getUsername(),
                    "token", token
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // ✅ FORGOT PASSWORD (Send mail)
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {

        String usernameOrEmail = body.get("usernameOrEmail");

        String msg = service.forgotPassword(usernameOrEmail);

        return ResponseEntity.ok(Map.of(
                "message", msg
        ));
    }

    // ✅ RESET PASSWORD
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {

        String token = body.get("token");
        String newPassword = body.get("newPassword");

        String msg = service.resetPassword(token, newPassword);

        return ResponseEntity.ok(Map.of(
                "message", msg
        ));
    }
    
//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest body) {
//        String msg = service.resetPassword(body.getToken(), body.getNewPassword());
//        return ResponseEntity.ok(Map.of("message", msg));
//    }
}