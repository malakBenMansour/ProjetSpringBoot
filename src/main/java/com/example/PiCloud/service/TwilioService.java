package com.example.PiCloud.service;

import com.example.PiCloud.entity.SmsRequest;
import com.example.PiCloud.entity.TwilioConfiguration;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioService(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    public void sendSms(SmsRequest smsRequest) {
        Twilio.init(
                twilioConfiguration.getAccountSid(),
                twilioConfiguration.getAuthToken()
        );
        Message.creator(
                        new PhoneNumber(smsRequest.getPhoneNumber()),
                        new PhoneNumber(twilioConfiguration.getTrialNumber()),
                        smsRequest.getMessage())
                .create();
    }
}

