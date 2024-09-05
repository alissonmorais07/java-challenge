package com.example.currencyConverter.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class CurrencyApiResponse {

    @JsonProperty("USDBRL")
    private CurrencyData usdBrl;
    @JsonProperty("EURBRL")
    private CurrencyData eurBrl;

    public CurrencyData getUsdBrl() {
        return usdBrl;
    }

    public void setUsdBrl(CurrencyData usdBrl) {
        this.usdBrl = usdBrl;
    }

    public CurrencyData getEurBrl() {
        return eurBrl;
    }

    public void setEurBrl(CurrencyData eurBrl) {
        this.eurBrl = eurBrl;
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

    public BigDecimal getExchangeRate(String currency) {
        CurrencyData currencyData = null;

        if ("USDBRL".equalsIgnoreCase(currency)) {
            currencyData = getUsdBrl();
        } else if ("EURBRL".equalsIgnoreCase(currency)) {
            currencyData = getEurBrl();
        }

        if (currencyData != null) {
            String ask = currencyData.getAsk();
            if (ask != null) {
                try {
                    return new BigDecimal(ask);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return BigDecimal.ZERO;
    }
}
