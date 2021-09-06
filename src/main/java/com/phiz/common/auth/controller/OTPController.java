package com.phiz.common.auth.controller;

import com.phiz.common.auth.service.OTPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = OTPController.OTP_BASE_PATH)
public class OTPController {

    public static final String OTP_BASE_PATH = "/otp";
    private OTPService otpService;

    public OTPController(OTPService otpService) {
        this.otpService = otpService;
    }

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> generateOTP(@RequestParam String mobileNumber) {

        Map<String, String> response = new HashMap<>(2);

        Boolean isGenerated = otpService.generateOtp(mobileNumber);

        if (!isGenerated) {
            response.put("status", "error");
            response.put("message", "OTP can not be generated.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("status", "success");
        response.put("message", "OTP successfully generated. Please check your e-mail!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> validateOTP(@RequestParam String mobileNumber, @RequestParam String code) {

        Map<String, String> response = new HashMap<>(2);
        boolean isValid = otpService.validateOTP(mobileNumber, Integer.valueOf(code));

        if (!isValid) {
            response.put("status", "error");
            response.put("message", "OTP is not valid!");

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        response.put("status", "success");
        response.put("message", "Entered OTP is valid!");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
