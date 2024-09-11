package com.example.currencyConverter.controller;

import com.example.currencyConverter.dto.request.ExchangeRequest;
import com.example.currencyConverter.dto.response.ExchangeResponse;
import com.example.currencyConverter.model.CurrencyConversion;
import com.example.currencyConverter.repository.CurrencyRepository;
import com.example.currencyConverter.service.CurrencyConversionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api")
public class CurrencyConversionController {

        private final CurrencyConversionService currencyConversionService;

    @Autowired
    private CurrencyConversionService conversionService;

    public CurrencyConversionController(CurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }

    @PostMapping("/convert")
    public ExchangeResponse convert(@Valid @RequestBody ExchangeRequest request) {
        return conversionService.convertCurrency(request);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CurrencyConversion>> findCurrencyRepositoryByUser(@RequestParam(required = false) Long userID){
        List<CurrencyConversion> conversions;

        if (userID == null) {
            conversions = currencyConversionService.findAllRecords();
        } else {
            conversions = currencyConversionService.findExchangeRecordsByUser(userID);
        }

        if (conversions.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(conversions);
    }
}
