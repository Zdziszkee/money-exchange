package me.zdziszkee.moneyexchange.service;

import me.zdziszkee.moneyexchange.client.RestClient;
import me.zdziszkee.moneyexchange.dto.CurrencyDto;
import me.zdziszkee.moneyexchange.dto.ExchangeInfo;
import me.zdziszkee.moneyexchange.dto.MoneyDto;
import me.zdziszkee.moneyexchange.dto.RateDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class MoneyExchangeServiceInterface {
    private static final CurrencyDto PLN_DTO = new CurrencyDto("PLN", "Złoty", List.of(new RateDto(LocalDate.now().toString(), 1.0, "039/A/NBP/2023")), "A");
    private final RestClient restClient;

    public MoneyExchangeServiceInterface(RestClient restClient) {
        this.restClient = restClient;
    }

    public BigDecimal exchange(final ExchangeInfo exchangeInfo) {

        MoneyDto money = exchangeInfo.money();
        String currency = exchangeInfo.targetCurrency();
        BigDecimal currencyValue = money.value();

        CurrencyDto currencyInfo;
        CurrencyDto targetCurrencyInfo;

        if (!money.currency().equals("PLN")) {
            currencyInfo = restClient.getCurrencyInfo(exchangeInfo.money().currency());
        } else {
            currencyInfo = PLN_DTO;
        }
        if (!currency.equals("PLN")) {
            targetCurrencyInfo = restClient.getCurrencyInfo(currency);
        } else {
            targetCurrencyInfo = PLN_DTO;
        }

        List<RateDto> currencyRates = currencyInfo.rates();
        RateDto currencyDto = currencyRates.stream().findAny().orElseThrow(() -> new IndexOutOfBoundsException("There is no data for this currency " + currencyInfo.currency()));
        Double currencyMid = currencyDto.mid();

        List<RateDto> targetCurrencyRates = targetCurrencyInfo.rates();
        RateDto targetCurrencyDto = targetCurrencyRates.stream().findAny().orElseThrow(() -> new IndexOutOfBoundsException("There is no data for this currency " + targetCurrencyInfo.currency()));
        Double targetCurrencyMid = targetCurrencyDto.mid();
        BigDecimal finalExchange = currencyValue.multiply(BigDecimal.valueOf(currencyMid / targetCurrencyMid));

        return finalExchange.setScale(2, RoundingMode.HALF_DOWN);
    }
}
