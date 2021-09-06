package com.phiz.common.auth.service;

public interface SMSUtilityService {

    Boolean sendSms(String mobileNumber, Integer code);
}
