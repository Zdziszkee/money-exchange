package me.zdziszkee.moneyexchange.service;

import me.zdziszkee.moneyexchange.client.RestClient;
import me.zdziszkee.moneyexchange.dto.CurrencyDto;
import me.zdziszkee.moneyexchange.dto.ExchangeInfo;
import me.zdziszkee.moneyexchange.dto.MoneyDto;
import me.zdziszkee.moneyexchange.dto.RateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MoneyExchangeServiceInterfaceTest {

    @Mock
    private RestClient mockRestClient;
    @InjectMocks
    private MoneyExchangeServiceInterface serviceInterface;

    @Test
    public void testExchangePlnToPln() {
        //Given
        String currency = "PLN";
        List<RateDto> rates = List.of(new RateDto(LocalDate.now().toString(), 1.0, "039/A/NBP/2023"));
        CurrencyDto currencyDto = new CurrencyDto("PLN", "Złoty", rates, "whatever");
        ExchangeInfo exchangeInfo = new ExchangeInfo(new MoneyDto("PLN", BigDecimal.valueOf(10)), "PLN");
        BigDecimal expectedValue = BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_DOWN);
        //When
        when(mockRestClient.getCurrencyInfo(currency)).thenReturn(currencyDto);
        BigDecimal exchange = serviceInterface.exchange(exchangeInfo);
        //Then
        assertEquals(expectedValue, exchange);
    }

    @Test
    public void testExchangeUsdToEur() {
        //Given
        String usdCurrency = "USD";
        List<RateDto> usdRates = List.of(new RateDto(LocalDate.now().toString(), 3.0, "039/A/NBP/2023"));
        CurrencyDto usdCurrencyDto = new CurrencyDto("USD", "Dolar Amerykański", usdRates, "whatever");

        String eurCurrency = "EUR";
        List<RateDto> eurRates = List.of(new RateDto(LocalDate.now().toString(), 1.5, "039/A/NBP/2023"));
        CurrencyDto eurCurrencyDto = new CurrencyDto("USD", "Dolar Amerykański", eurRates, "whatever");

        ExchangeInfo exchangeInfo = new ExchangeInfo(new MoneyDto("USD", BigDecimal.valueOf(10)), "EUR");
        BigDecimal expectedValue = BigDecimal.valueOf(20.00).setScale(2, RoundingMode.HALF_DOWN);

        //When
        when(mockRestClient.getCurrencyInfo(usdCurrency)).thenReturn(usdCurrencyDto);
        when(mockRestClient.getCurrencyInfo(eurCurrency)).thenReturn(eurCurrencyDto);

        BigDecimal exchange = serviceInterface.exchange(exchangeInfo);
        //Then
        assertEquals(expectedValue, exchange);

    }

    @Test
    public void testExchangePlnToUsd() {
        //Given
        String currency = "USD";
        List<RateDto> rates = List.of(new RateDto(LocalDate.now().toString(), 1.5, "039/A/NBP/2023"));
        CurrencyDto currencyDto = new CurrencyDto("USD", "Dolar Amerykański", rates, "whatever");
        ExchangeInfo exchangeInfo = new ExchangeInfo(new MoneyDto("PLN", BigDecimal.valueOf(10)), "USD");
        BigDecimal expectedValue = BigDecimal.valueOf(6.67).setScale(2, RoundingMode.HALF_DOWN);
        //When
        when(mockRestClient.getCurrencyInfo(currency)).thenReturn(currencyDto);
        BigDecimal exchange = serviceInterface.exchange(exchangeInfo);
        //Then
        assertEquals(expectedValue, exchange);
    }

    @Test
    public void testExchangeUsdToPln() {
        //Given
        String currency = "USD";
        List<RateDto> rates = List.of(new RateDto(LocalDate.now().toString(), 1.5, "039/A/NBP/2023"));
        CurrencyDto currencyDto = new CurrencyDto("USD", "Dolar Amerykański", rates, "whatever");
        ExchangeInfo exchangeInfo = new ExchangeInfo(new MoneyDto("USD", BigDecimal.valueOf(10)), "PLN");
        BigDecimal expectedValue = BigDecimal.valueOf(15.00).setScale(2, RoundingMode.HALF_DOWN);
        //When
        when(mockRestClient.getCurrencyInfo(currency)).thenReturn(currencyDto);
        BigDecimal exchange = serviceInterface.exchange(exchangeInfo);
        //Then
        assertEquals(expectedValue, exchange);
    }

    @Test
    public void testIndexOutOfBoundException() {
        //Given
        String currency = "USD";
        List<RateDto> rates = List.of();
        CurrencyDto currencyDto = new CurrencyDto("USD", "Dolar Amerykański", rates, "whatever");
        ExchangeInfo exchangeInfo = new ExchangeInfo(new MoneyDto("USD", BigDecimal.valueOf(10)), "PLN");
        //When
        when(mockRestClient.getCurrencyInfo(currency)).thenReturn(currencyDto);
        Executable when = () -> serviceInterface.exchange(exchangeInfo);
        //Then
        assertThrows(IndexOutOfBoundsException.class, when);
    }

}