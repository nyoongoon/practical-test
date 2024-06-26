package sample.cafekiosk.spring.api.service.product.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 강의에서는 requestDto의 각 파라미터에 대한 모든 검증 test 메서드를 작성함.. -> 너무 많지 않나..?
 * --> 컨트롤러 테스트에서 진행
 */
@Getter
@NoArgsConstructor // objectMapper가 사용
public class ProductCreateServiceRequest {

    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    public ProductCreateServiceRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    /**
     * dto -> entity 변환 책임을 dto가 갖고 product의 빌더를 사용했다.
     * 내생각 : dto가 변환 책임을 갖는 건 좋으나, product 생성자 로직이 복잡하면서 고정적인 경우 메소드 내부에서 product의 빌더대신 product 팩토리 메서드를 사용하는 것이 좋을 것 같다..
     */
    public Product toEntity(String nextProductNumber) {
        return Product.builder()
                .productNumber(nextProductNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}
