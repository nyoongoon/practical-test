package sample.cafekiosk.spring.domain.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
public class ProductCreateRequest {

    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    public ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
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
