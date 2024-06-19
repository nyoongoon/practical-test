package sample.cafekiosk.spring.domain.product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.BaseEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_number")
    private String productNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ProductType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "selling_status")
    private ProductSellingStatus sellingStatus;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;
}
