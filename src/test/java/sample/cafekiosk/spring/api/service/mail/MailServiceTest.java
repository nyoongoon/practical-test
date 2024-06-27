package sample.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.client.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock //@ExtendWith(MockitoExtension.class) 필요.
    private MailSendClient mailSendClient;

    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail() {
        //given
        // 필드 선언으로 대체 가능 -> @Mock, @InjectMocks
//        MailSendClient mailSendClient = Mockito.mock(MailSendClient.class);
//        MailSendHistoryRepository mailSendHistoryRepository = Mockito.mock(MailSendHistoryRepository.class);
//        MailService mailService = new MailService(mailSendClient, mailSendHistoryRepository);

        //MailSendClient Mock객체에 대한 Stubbing
        Mockito.when(mailSendClient.sendMail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(true); //내부에서  mailSendHistoryRepository.save()의 결과로 null 반환 -> 목객체는 기본설정으로 반환값을 기본값으로 리턴함(ex- int->0)

        //when
        boolean result = mailService.sendMail("", "", "", "");

        //then
        assertThat(result).isTrue();
        // Mock 객체의 특정 메서드가 몇번 호출되었는지 검증하기..
        Mockito.verify(mailSendHistoryRepository, Mockito.times(1)).save(any(MailSendHistory.class));
    }
}