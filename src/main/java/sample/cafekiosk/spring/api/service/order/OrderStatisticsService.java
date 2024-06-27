package sample.cafekiosk.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.service.mail.MailService;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.order.OrderStatus;

import java.time.LocalDate;
import java.util.List;


/**
 * 메일 전송을 하는 행위에는 @Transactional을 안붙이는 게 좋음 !
 * mail 전송 같은 네트워크를 타는 등의 오래걸리는 작업 동안 트랜잭션이 살아있어 성능이 저하됨
 */
@RequiredArgsConstructor
@Service
public class OrderStatisticsService {
    private final OrderRepository orderRepository;
    private final MailService mailService;

    public boolean sendOrderStatisticsMail(LocalDate orderDate, String email) {
        // 해당 일자에 결제완료된 주문들을 가져와서
        List<Order> orders = orderRepository.findOrderBy(
                orderDate.atStartOfDay(),
                orderDate.plusDays(1).atStartOfDay(),
                OrderStatus.PAYMENT_COMPLETED
        );

        // 총 매출 합계를 계산하고
        int totalAmount = orders.stream()
                .mapToInt(Order::getTotalPrice)
                .sum();

        /**
         * 테스트를 돌릴 떄마다 매일을 보내야하는가? 라는 고민
         */
        // 메일 전송
        boolean result = mailService.sendMail("no-reply@cafekiosk.com",
                email,
                String.format("[매출통계] %s", orderDate),
                String.format("총 매출 합계는 %s원입니다.", totalAmount)
        );

        if(!result){
            throw new IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.");
        }

        return true;
    }
}
