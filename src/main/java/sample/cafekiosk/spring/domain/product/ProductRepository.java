package sample.cafekiosk.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);
    /**
     * where in 절에 중복 조건 들어가도 반환값은 중복없이 나온다.
     */
    List<Product> findAllByProductNumberIn(List<String> productNumbers);

    // 쿼리 구현 방식은 상관없음 -> 구현에 상관없이 테스트를 잘 해야한다는 의미..
    @Query(value = "select p.product_number from product p order by id desc limit 1", nativeQuery = true)
    String findLatestProductNumber();
}
