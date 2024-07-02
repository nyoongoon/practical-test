package sample.cafekiosk.spring.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductNumberFactory {
    private final ProductRepository productRepository;

    /**
     * 역할 분리의 필요성이 생겨서 Factory클래스를 만든 후 public 메서드로 열어주는 리팩토링 진행
     *
     * @return
     */
    public String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (latestProductNumber == null) {
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);
    }
}
