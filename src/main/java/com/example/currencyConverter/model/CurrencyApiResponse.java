package com.example.currencyConverter.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CurrencyApiResponse {

    private final Map<String, CurrencyData> currencyDataMap = new HashMap<>();

    @JsonAnySetter
    public void setCurrencyData(String key, CurrencyData value) {
        currencyDataMap.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, CurrencyData> getCurrencyDataMap() {
        return currencyDataMap;
    }

    @Getter
    @Setter
    public static class CurrencyData {
        private String bid;
        private String ask;
    }

    public BigDecimal getExchangeRate(String currencyPair) {
        CurrencyData currencyData = currencyDataMap.get(currencyPair.toUpperCase());
        if (currencyData != null && currencyData.getAsk() != null) {
            try {
                return new BigDecimal(currencyData.getAsk());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return BigDecimal.ZERO;
    }
}
