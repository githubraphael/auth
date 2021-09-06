package com.phiz.common.auth.service;

public interface OTPService {

    Boolean generateOtp(String key);

    Boolean validateOTP(String key, Integer otpNumber);
}
