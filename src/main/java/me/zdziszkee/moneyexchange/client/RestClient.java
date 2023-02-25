package me.zdziszkee.moneyexchange.client;

import me.zdziszkee.moneyexchange.dto.CurrencyDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {
    private final RestTemplate restTemplate;

    public RestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CurrencyDto getCurrencyInfo(String currencyCode) {
        CurrencyDto data = restTemplate.getForObject("/exchangerates/rates/a/" + currencyCode, CurrencyDto.class);
        return data;
    }
}
