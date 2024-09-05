package com.example.currencyConverter.controller;

import com.example.currencyConverter.dto.request.ExchangeRequest;
import com.example.currencyConverter.dto.response.ExchangeResponse;
import com.example.currencyConverter.service.CurrencyConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/api")
public class CurrencyConversionController {


    @Autowired
    private CurrencyConversionService conversionService;

    @PostMapping("/convert")
    public ExchangeResponse convert(@RequestBody ExchangeRequest request) {
        return conversionService.convertCurrency(request);
    }

    @GetMapping("/list")
    public ExchangeResponse convertCurrencyByParams(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam BigDecimal value,
            @RequestParam Long userID) {

        ExchangeRequest request = new ExchangeRequest();
        request.setFromCurrency(fromCurrency);
        request.setToCurrency(toCurrency);
        request.setValue(value);
        request.setUserID(userID);

        return conversionService.convertCurrency(request);
    }

}
