package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.dto.request.ProductCreateRequest;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Transactional(readOnly = true)
 * readOnly = true -> 읽기 전용 트랜잭션..
 * CRUD에서 CUD 동작 X / only read..
 * JPA : CUD 스냅샷 저장, 변경감지 X (성능 향상)
 *
 * CQRS - Command / Read --> 서비스 분리도 추천
 *
 * --> 어노테이션으로 메서드 구분하여 DB에 대한 엔드포인트를 구분을 할 수가 있다
 */
@Transactional(readOnly = true) // 누락 이슈 때문에 readOnly 클래스 레벨에 달 것을 추천
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * 동시성 이슈 고려 필요.. -> 유니크 제약조건 혹은 UUID 등..
     */
    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        //productNumber 부여..
        String nextProductNumber = createNextProductNumber();
        // nextProductNumber..

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

//    @Transactional(readOnly = true) 누락 가능성 때문에 readOnly 어노테이션은 클래스 레벨에 다는 것을 추천한다.
    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (latestProductNumber == null) {
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);
    }
}
