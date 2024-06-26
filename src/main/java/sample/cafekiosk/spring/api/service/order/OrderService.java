package sample.cafekiosk.spring.api.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.order.response.OrderResponse;
import sample.cafekiosk.spring.api.service.order.request.OrderCreateServiceRequest;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.stock.Stock;
import sample.cafekiosk.spring.domain.stock.StockRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    /**
     * 재고감소 -> 동시성 문제의 대표적인 예시
     * @param request
     * @param registeredDateTime
     * @return
     */
    public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        List<Product> products = findProductsBy(productNumbers);

        deductStockQuantities(products);

        Order order = Order.create(products, registeredDateTime);

        /**
         * 강의에서는 채번이 된 뒤에 response에 담아야한다고 saved를 받고 saved를 response에 넣었는데 그러지 않아도 될듯..?
         * -> 아이덴티티전략이므로 save시 db갔다옴..
         */
//        Order saved = orderRepository.save(order);
        orderRepository.save(order);
        return OrderResponse.of(order);
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        //Product
        /**
         * where in 절에 중복 조건 들어가도 반환값은 중복없이 나온다.
         */
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        /**
         * 중복 없이 조회한 상품 정보에 대해 중복 주문 요청도 처리될 수 있는 로직
         */
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        List<Product> duplicateProducts = productNumbers.stream()
                .map(productMap::get)
                .collect(Collectors.toList());
        return duplicateProducts;
    }

    private void deductStockQuantities(List<Product> products) {
        // 재고 차감 체크가 필요한 상품 filter
        // 재고 엔티티 조회
        // 상품별 counting
        // 재고 차감 시도
        List<String> stockProductNumbers = extractStockProductNumbers(products); // 가공로직 메서드로 만들면 명명할 수 있어서 좋음!

        // 재고 엔티티 조회
        Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);

        // 상품별 counting
        Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

        /**
         * 향상된 for문 안에서 바로 HashSet에 넣어서 중복 제거하는 코딩 스타일 기억하기..
         */
        // 재고 차감 시도
        for(String stockProductNumber : new HashSet<>(stockProductNumbers)){ // 중복제거..
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();
            if(stock.isQuantityLessThen(quantity)){
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            /**
             * 위에서 재고 체크를 하고 아래 내부 메소드 안에서도 재고 체크를 한다..
             * -> 서비스 로직에서의 예외는 주문 생성 로직을 수행하다가 발생한 것이고
             * deductQuantity()에선 단위 테스트의 개념으로 독자적인 체크 로직이 필요하 -> 에러 메시지도 다름..
             */
            stock.deductQuantity(quantity);

        }
    }

    private List<String> extractStockProductNumbers(List<Product> products) {
        List<String> stockProductNumbers = products.stream()
                .filter(product -> ProductType.containsStockType(product.getType()))
                .map(Product::getProductNumber)
                .collect(Collectors.toList());
        return stockProductNumbers;
    }

    private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
        Map<String, Stock> stockMap = stocks.stream()
                .collect(Collectors.toMap(Stock::getProductNumber, s -> s));
        return stockMap;
    }

    private Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
        Map<String, Long> productCountingMap = stockProductNumbers.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        return productCountingMap;
    }
}
