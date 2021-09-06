package com.phiz.common.auth.service.impl;

import com.phiz.common.auth.service.SMSUtilityService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class SMSUtilityServiceImpl implements SMSUtilityService {

    @Value("${twillo.account_sid}")
    public String ACCOUNT_SID;

    @Value("${twillo.auth_token}")
    public String AUTH_TOKEN;

    @Value("${twillo.msg_sid}")
    public String MSG_SID;

    @Override
    public Boolean sendSms(String mobileNumber, Integer code) {

        Boolean smsSent = Boolean.FALSE;
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(mobileNumber),
                MSG_SID,
                "Your One-Time PIN is " + code).create();

        log.info("Message status : {}", message.getStatus());
        if (Objects.nonNull(message) && (message.getStatus().equals(Message.Status.ACCEPTED))) {
            smsSent = Boolean.TRUE;
        }

        return smsSent;
    }


}
