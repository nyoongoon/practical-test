package sample.cafekiosk.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
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

        Order order = Order.create(duplicateProducts, registeredDateTime);

        /**
         * 강의에서는 채번이 된 뒤에 response에 담아야한다고 saved를 받고 saved를 response에 넣었는데 그러지 않아도 될듯..?
         * -> 아이덴티티전략이므로 save시 db갔다옴..
         */
//        Order saved = orderRepository.save(order);
        orderRepository.save(order);
        return OrderResponse.of(order);
    }
}
