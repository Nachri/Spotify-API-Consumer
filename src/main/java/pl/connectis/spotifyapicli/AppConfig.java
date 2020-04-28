package pl.connectis.spotifyapicli;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Lazy
@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, @Value("${baseUrl}") String baseUrl) {
        return builder.rootUri(baseUrl).build();
    }

    @Bean
    public ReentrantLock lock() {
        return new ReentrantLock();
    }

    @Bean
    public Condition condition() {
        return lock().newCondition();
    }

}
