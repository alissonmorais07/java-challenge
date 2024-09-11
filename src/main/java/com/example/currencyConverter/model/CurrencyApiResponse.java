package com.example.currencyConverter.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CurrencyApiResponse {

    // Mapa para armazenar todos os pares de moedas e seus dados
    private final Map<String, CurrencyData> currencyDataMap = new HashMap<>();

    @JsonAnySetter
    public void setCurrencyData(String key, CurrencyData value) {
        currencyDataMap.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, CurrencyData> getCurrencyDataMap() {
        return currencyDataMap;
    }

    public static class CurrencyData {
        private String bid;
        private String ask;

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public String getAsk() {
            return ask;
        }

        public void setAsk(String ask) {
            this.ask = ask;
        }
    }

    // Método para obter a taxa de câmbio de um par específico
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
