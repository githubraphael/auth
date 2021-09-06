package com.phiz.common.auth.service.impl;

import com.phiz.common.auth.service.OTPService;
import com.phiz.common.auth.service.OTPUtilityService;
import com.phiz.common.auth.service.SMSUtilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class OTPServiceImpl implements OTPService {

    private OTPUtilityService otpUtilityService;
    private SMSUtilityService smsUtilityService;

    public OTPServiceImpl(OTPUtilityService otpUtilityService, SMSUtilityService smsUtilityService) {
        this.otpUtilityService = otpUtilityService;
        this.smsUtilityService = smsUtilityService;
    }


    @Override
    public Boolean generateOtp(String key) {

        Integer code = otpUtilityService.generateOTP(key);
        log.info("Key : {} Code :{}", key, code);

        Boolean msgSent = smsUtilityService.sendSms(key, code);

        return msgSent;
    }

    @Override
    public Boolean validateOTP(String key, Integer otpNumber) {

        Integer cacheOTP = otpUtilityService.getOPTByKey(key);

        if (Objects.nonNull(cacheOTP) && cacheOTP.equals(otpNumber)) {

            otpUtilityService.clearOTPFromCache(key);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
