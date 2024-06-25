package sample.cafekiosk.spring.api.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.product.dto.request.ProductCreateRequest;

/**
 * @EnableJpaAuditing --> webMvcTest로 인해 분리
 */
// 컨트롤러 관련 빈들만 올릴 수 있는 테스트 어노테이션
@WebMvcTest(controllers = ProductControllerTest.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean //컨테이너에 Mockito로 만든 목 객체를 넣어주는 역할을 함
    private ProductService productService;

    @Test
    @DisplayName("신규 상품을 등록한다.")
    void createProduct() throws Exception {
        //given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());


    }
}