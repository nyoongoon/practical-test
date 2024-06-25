package sample.cafekiosk.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing --> webMvcTest로 인해 분리
@SpringBootApplication
public class CafekioskApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafekioskApplication.class, args);
    }

}
