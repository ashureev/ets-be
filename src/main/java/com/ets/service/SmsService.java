package com.ets.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {

    /**
     * Mock method to simulate sending an SMS.
     * In a real-world scenario, you would integrate with an SMS API like Twilio, Vonage, etc.
     */
    public void sendRegistrationSms(String phoneNumber, String name, String otp) {
        System.out.println("Processing SMS Notification...");
        System.out.println("To: " + phoneNumber);
        System.out.println("Message: Dear " + name + ", your registration is successful. Your verification OTP is: " + otp);
        
        // Integration Logic Template:
        // 1. Get API Key/Secret from properties
        // 2. Build HTTP request to SMS Gateway
        // 3. Send and handle response
    }
}
