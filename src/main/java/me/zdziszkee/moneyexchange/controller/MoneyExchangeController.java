package me.zdziszkee.moneyexchange.controller;

import me.zdziszkee.moneyexchange.dto.ExchangeInfo;
import me.zdziszkee.moneyexchange.service.MoneyExchangeServiceInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class MoneyExchangeController {
    private final MoneyExchangeServiceInterface service;

    public MoneyExchangeController(MoneyExchangeServiceInterface service) {
        this.service = service;
    }

    @GetMapping("/exchange")
    public BigDecimal exchange(@RequestBody ExchangeInfo exchangeInfo) {
        return service.exchange(exchangeInfo);
    }
}
