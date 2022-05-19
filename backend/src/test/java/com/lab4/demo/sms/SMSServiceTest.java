package com.lab4.demo.sms;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class SMSServiceTest {

    @InjectMocks
    private SMSService smsService;

    @Test
    void send() {
        smsService.send();
    }
}