package uz.mediasolutions.barterlybackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OtpService {

    private Map<String, String> otps = new HashMap<>();

    public void generateOtp(String phoneNumber) {
        String otp = String.valueOf((int) ((Math.random() * (9999 - 1000)) + 1000));
        otps.put(phoneNumber, otp);
        // todo "should send sms to phone number"
        System.out.println(phoneNumber + " generated otp: " + otp);
    }


    public boolean validateOtp(String phoneNumber, String otp) {
        String s = otps.get(phoneNumber);
        if (otps != null && s.equals(otp)) {
            otps.remove(phoneNumber, otp);
            return true;
        }
        return false;
    }

}
