package sample.cafekiosk.spring.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
public abstract class RestDocsSupport {

    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(
            RestDocumentationContextProvider provider
    ) {
        //mockMvc 직접 만들기
        this.mockMvc = MockMvcBuilders.standaloneSetup(initController()) // 하위에서 컨트롤러 주입하도록 추상메서드 선언

                .apply(documentationConfiguration(provider))
                .build();
    }

    abstract protected Object initController();

    /**
     * 어플리케이션을 띄우는 방식 -> 굳이..
     */
//    @BeforeEach
//    void setUp(
//            WebApplicationContext webApplicationContext,
//            RestDocumentationContextProvider provider
//    ){
//        //mockMvc 직접 만들기
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext) //어플리케이션을 띄우는 방식 ->@SpringBootTest 필요
//
//                .apply(documentationConfiguration(provider))
//                .build();
//    }
}
