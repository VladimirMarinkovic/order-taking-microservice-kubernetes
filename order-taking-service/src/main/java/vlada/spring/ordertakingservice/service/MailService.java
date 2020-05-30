package vlada.spring.ordertakingservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import vlada.spring.ordertakingservice.exception.ApiException;

@Service
@Slf4j
public class MailService {

    @Autowired
    private JavaMailSender mailSender;


    void sendMail(String recipient, String message) {
        MimeMessagePreparator messagePreparator = m -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(m);
            messageHelper.setFrom("ordertaiking@email.com");
            messageHelper.setTo(recipient);
            messageHelper.setSubject("Order is in process");
            messageHelper.setText(message);
            log.info("Mail send to  >> {}", recipient);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            throw new ApiException("Exception occurred when sending mail to " + recipient);
        }
    }
}
