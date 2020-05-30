package vlada.spring.ordertakingservice.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;



@Service
@Slf4j
public class CustomRabbitListener implements MessageListener {


    @Override
    public void onMessage(Message message) {
        log.info("Received a new message = [" + new String(message.getBody()) + "]");
    }
}

