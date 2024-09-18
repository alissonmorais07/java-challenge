package com.example.currencyConverter.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ExchangeRequest {

    @NotNull(message = "The user ID cannot be null!")
    private Long userID;
    @NotEmpty(message = "Source currency cannot be empty!")
    @Size(min = 3, max = 3, message = "The source currency must have exactly 3 characters!")
    private String fromCurrency;
    @NotEmpty(message = "Target currency cannot be empty!")
    @Size(min = 3, max = 3, message = "The target currency must have exactly 3 characters!")
    private String toCurrency;
    @NotNull(message = "The value cannot be null!")
    @Positive(message = "The value must be positive!")
    private BigDecimal value;
}
