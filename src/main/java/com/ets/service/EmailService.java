package com.ets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String toEmail, String name, String phone, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Registration Successful - ETS Portal");
        message.setText("Dear " + name + ",\n\n" +
                "You have been successfully registered on the ETS Portal.\n\n" +
                "Registered Details:\n" +
                "Email: " + toEmail + "\n" +
                "Phone: " + (phone != null ? phone : "Not provided") + "\n" +
                "Verification OTP: " + otp + "\n\n" +
                "Please use this OTP for your first login.\n\n" +
                "Welcome aboard!\n\n" +
                "Best Regards,\n" +
                "Admin Team");
        
        try {
            System.out.println("Attempting to send registration email to: " + toEmail);
            mailSender.send(message);
            System.out.println("✓ SUCCESS: Registration email transmitted to " + toEmail);
        } catch (Exception e) {
            System.err.println("❌ ERROR: Failed to send email to " + toEmail);
            System.err.println("Reason: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
