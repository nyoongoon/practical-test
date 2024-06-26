package sample.cafekiosk.spring.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSendClient {
    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {
        // 메일전송
        log.info("메일 전송");
        return true;
    }
}
