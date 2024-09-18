package com.example.currencyConverter.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "currency_conversion")
public class CurrencyConversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionID;

    private Long userID;
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal fromValue;
    private BigDecimal toValue;
    private BigDecimal exchangeRate;
    private LocalDateTime dateTime;
}
