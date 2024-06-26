package sample.cafekiosk.spring.api.controller.product.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
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
public class ProductCreateRequest {

    /**
     * 검증에서 에러가 발생했을 때도 ApiResponse처럼 규격화된 응답을 보내야함
     * -> 에러발생 시 BindException -> ExceptionHandler 핸들링 추가하기..
     */
    @NotNull(message = "상품 타입은 필수입니다.")
    private ProductType type;
    @NotNull(message = "상품 판매 상태는 필수입니다.")
    private ProductSellingStatus sellingStatus;

    //@NotNull -> "", " " => 통과됨
    //@NotEmpty -> " " => 통과됨
    @NotBlank(message = "상품 이름은 필수입니다.") // 공백까지 통과 시키지 않음
    private String name;
    /**
     * validation에 대한 책임 분리..
     *
     * @Max 같은 것이 이곳에서 검증하는 것이 맞나라는 생각
     * 상품 이름 20자 제한이 있다고 할 때. 컨트롤러에서 검증하는 것이 맞는가?
     * 컨트롤러에서는 NotBlank같은 기본 검증을 하고
     * -> 서비스 레이어에서나 도메인 객체 생성자에서나 검증을 하는 것을 추천한다..
     */

    @Positive(message = "상품 가격은 양수여야합니다.")
    private int price;

    @Builder
    public ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public ProductCreateServiceRequest toServiceRequest() {
        return ProductCreateServiceRequest.builder()
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }


    // --> 서비스 DTO로 이전
//    /**
//     * dto -> entity 변환 책임을 dto가 갖고 product의 빌더를 사용했다.
//     * 내생각 : dto가 변환 책임을 갖는 건 좋으나, product 생성자 로직이 복잡하면서 고정적인 경우 메소드 내부에서 product의 빌더대신 product 팩토리 메서드를 사용하는 것이 좋을 것 같다..
//     */
//    public Product toEntity(String nextProductNumber) {
//        return Product.builder()
//                .productNumber(nextProductNumber)
//                .type(type)
//                .sellingStatus(sellingStatus)
//                .name(name)
//                .price(price)
//                .build();
//    }
}
