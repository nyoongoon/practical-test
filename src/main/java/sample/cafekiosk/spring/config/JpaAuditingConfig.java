package sample.cafekiosk.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // --> webMvcTest로 인해 분리 --> 원리 찾아보기...
@Configuration
public class JpaAuditingConfig {
}
