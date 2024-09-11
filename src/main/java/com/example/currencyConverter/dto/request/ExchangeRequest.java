package com.example.currencyConverter.dto.request;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

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

    public @NotNull(message = "The user ID cannot be null!") Long getUserID() {
        return userID;
    }

    public void setUserID(@NotNull(message = "The user ID cannot be null!") Long userID) {
        this.userID = userID;
    }

    public @NotEmpty(message = "Source currency cannot be empty!") @Size(min = 3, max = 3, message = "The source currency must have exactly 3 characters!") String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(@NotEmpty(message = "Source currency cannot be empty!") @Size(min = 3, max = 3, message = "The source currency must have exactly 3 characters!") String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public @NotEmpty(message = "Target currency cannot be empty!") @Size(min = 3, max = 3, message = "The target currency must have exactly 3 characters!") String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(@NotEmpty(message = "Target currency cannot be empty!") @Size(min = 3, max = 3, message = "The target currency must have exactly 3 characters!") String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public @NotNull(message = "The value cannot be null!") @Positive(message = "The value must be positive!") BigDecimal getValue() {
        return value;
    }

    public void setValue(@NotNull(message = "The value cannot be null!") @Positive(message = "The value must be positive!") BigDecimal value) {
        this.value = value;
    }
}
