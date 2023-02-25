package me.zdziszkee.moneyexchange.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration("me.zdziszkee.moneyexchange")
public class SpringConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().rootUri("http://api.nbp.pl/api").build();
    }
}
