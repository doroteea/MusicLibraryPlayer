package com.lab4.demo.sms;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;

@Component
public class SMSService {
    private final String ACCOUNT_SID = "AC10cba7b1e53ffe5c5bd128a65059d259";

    private final String AUTH_TOKEN = "c43c9c1eff4c4273e8a38805fb3bfdad";

    private final String FROM_NUMBER = "+18783488566";

    public void send() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber("+40787334208"), new PhoneNumber(FROM_NUMBER), "Best Music Player Library Ever!")
                .create();

    }
}
