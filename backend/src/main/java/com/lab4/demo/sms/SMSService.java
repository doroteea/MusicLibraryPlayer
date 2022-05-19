package com.lab4.demo.sms;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;

@Component
public class SMSService {
    private final String ACCOUNT_SID = "AC10cba7b1e53ffe5c5bd128a65059d259";

    private final String AUTH_TOKEN = "3e495318badcaa39249ebd2bfc63b0fd";

    private final String FROM_NUMBER = "+18783488566";

    public void send() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        System.out.println("here");
        Message message = Message.creator(new PhoneNumber("+40787334208"), new PhoneNumber(FROM_NUMBER), "Best Music Player Library Ever!")
                .create();

    }
}
