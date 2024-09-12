package com.example.currencyConverter.controller;

import com.example.currencyConverter.dto.request.ExchangeRequest;
import com.example.currencyConverter.dto.response.ExchangeResponse;
import com.example.currencyConverter.model.CurrencyConversion;
import com.example.currencyConverter.service.CurrencyConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(description = "Perform currency conversion and save the transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the currency conversion record."),
            @ApiResponse(responseCode = "400", description = "Returns request processing error.")
    })
    @PostMapping("/convert")
    public ExchangeResponse convert(@Valid @RequestBody ExchangeRequest request) {
        return conversionService.convertCurrency(request);
    }

    @Operation(description = "Displays the general and user conversion list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the general and user conversion list."),
            @ApiResponse(responseCode = "400", description = "Returns request processing error.")
    })
    @GetMapping("/list")
    public ResponseEntity<List<CurrencyConversion>> findCurrencyRepositoryByUser(@RequestParam(required = false) Long userID) {
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
