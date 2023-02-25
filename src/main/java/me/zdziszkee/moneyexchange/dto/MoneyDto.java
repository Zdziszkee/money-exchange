package me.zdziszkee.moneyexchange.dto;

import java.math.BigDecimal;

public record MoneyDto(String currency, BigDecimal value) {
}
