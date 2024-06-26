package sample.cafekiosk.spring.api.service.order.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateServiceRequest {
    // 서비스 DTO에서는 validation이 필요 없다... // 추후 모듈 분리시 서비스 모듈이 validation의존성 없어도 됨
//    @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
    private List<String> productNumbers;

    @Builder
    public OrderCreateServiceRequest(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }
}
