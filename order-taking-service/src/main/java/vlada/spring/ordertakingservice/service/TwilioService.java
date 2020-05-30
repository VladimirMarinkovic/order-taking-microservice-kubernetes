package vlada.spring.ordertakingservice.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vlada.spring.ordertakingservice.configuration.TwilioConfiguration;
import vlada.spring.ordertakingservice.model.Sms;
import com.twilio.type.PhoneNumber;


@Service("twilio")
@Slf4j
public class TwilioService  {


    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioService(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    public void sendSms(Sms smsRequest) {
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
            String message = smsRequest.getMessage();
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            log.info("Send sms {}", smsRequest);
    }

}