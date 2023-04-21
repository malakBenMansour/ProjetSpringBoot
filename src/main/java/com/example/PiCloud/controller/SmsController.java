package com.example.PiCloud.controller;


import com.example.PiCloud.entities.SmsRequest;
import com.example.PiCloud.services.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
    @RequestMapping("/api/sms")
    public class SmsController {

        private final TwilioService twilioService;

        @Autowired
        public SmsController(TwilioService twilioService) {
            this.twilioService = twilioService;
        }

        @PostMapping
        public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
            twilioService.sendSms(smsRequest);
        }
    }


