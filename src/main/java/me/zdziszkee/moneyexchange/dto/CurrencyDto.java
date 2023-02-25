package me.zdziszkee.moneyexchange.dto;

import java.util.List;

public record CurrencyDto(String code, String currency, List<RateDto> rates, String table) {
}




