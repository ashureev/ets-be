package com.ets.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.ets.dto.ResetPasswordRequest;
import com.ets.service.AdminLoginService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminLoginController {

    private final AdminLoginService service;

    public AdminLoginController(AdminLoginService service) {
        this.service = service;
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        String result = service.adminLogin(username, password);

        return ResponseEntity.ok(Map.of(
                "message", "Login Success",
                "result", result
        ));
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