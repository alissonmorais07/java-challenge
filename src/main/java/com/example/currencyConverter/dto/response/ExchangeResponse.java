package com.example.currencyConverter.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExchangeResponse {
    private Long transactionID;
    private Long userID;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal fromValue;
    private BigDecimal toValue;
    private BigDecimal exchangeRate;
    private LocalDateTime dateTime;
}
