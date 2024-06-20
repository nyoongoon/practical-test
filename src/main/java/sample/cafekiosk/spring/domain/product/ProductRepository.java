package sample.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);
    /**
     * where in 절에 중복 조건 들어가도 반환값은 중복없이 나온다.
     */
    List<Product> findAllByProductNumberIn(List<String> productNumbers);
}
