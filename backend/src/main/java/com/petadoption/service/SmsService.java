package com.petadoption.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * SMS Service for sending OTP via Twilio
 */
@Service
@RequiredArgsConstructor
public class SmsService {

    @Value("${twilio.account-sid:}")
    private String accountSid;

    @Value("${twilio.auth-token:}")
    private String authToken;

    @Value("${twilio.phone-number:}")
    private String twilioPhoneNumber;

    /**
     * Send OTP via SMS to phone number
     */
    public void sendOtpSms(String phoneNumber, String otp) {
        try {
            if (accountSid == null || accountSid.isEmpty() || 
                authToken == null || authToken.isEmpty()) {
                System.out.println("[WARN] Twilio credentials not configured. Skipping SMS.");
                return;
            }

            Twilio.init(accountSid, authToken);

            String messageBody = "Your Pet Adoption OTP is: " + otp + ". Valid for 10 minutes. Do not share this code.";

            Message message = Message.creator(
                    new PhoneNumber(twilioPhoneNumber),  // From number (Twilio phone)
                    new PhoneNumber("+91" + phoneNumber), // To number (formatted with country code)
                    messageBody
            ).create();

            System.out.println("[INFO] SMS sent successfully. Message SID: " + message.getSid());

        } catch (Exception e) {
            System.out.println("[ERROR] Failed to send SMS: " + e.getMessage());
            // Don't throw exception - allow registration to continue even if SMS fails
        }
    }
}