package com.example.currencyConverter.service;

import com.example.currencyConverter.dto.request.ExchangeRequest;
import com.example.currencyConverter.dto.response.ExchangeResponse;
import com.example.currencyConverter.model.CurrencyApiResponse;
import com.example.currencyConverter.model.CurrencyConversion;
import com.example.currencyConverter.repository.CurrencyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CurrencyConversionService {

    private final RestTemplate restTemplate;
    private final CurrencyRepository currencyRepository;

    public CurrencyConversionService(RestTemplate restTemplate, CurrencyRepository currencyRepository) {
        this.restTemplate = restTemplate;
        this.currencyRepository = currencyRepository;
    }

    public ExchangeResponse convertCurrency(ExchangeRequest request) {
        String url = String.format("https://economia.awesomeapi.com.br/json/last/%s-%s",
                request.getFromCurrency(), request.getToCurrency());

        CurrencyApiResponse apiResponse;
        try {
            apiResponse = restTemplate.getForObject(url, CurrencyApiResponse.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Error when trying to connect to the exchange API: " + e.getMessage(), e);
        }

        if (apiResponse == null) {
            throw new RuntimeException("The API response is null. Check the URL and service availability.");
        }

        String key = request.getFromCurrency() + request.getToCurrency();
        BigDecimal exchangeRate = apiResponse.getExchangeRate(key);

        if (exchangeRate == null) {
            throw new RuntimeException("Exchange rate not found for currency pair: " + key);
        }

        BigDecimal toValue = request.getValue().multiply(exchangeRate);

        CurrencyConversion conversion = createCurrencyConversion(request, exchangeRate, toValue);
        CurrencyConversion savedConversion = currencyRepository.save(conversion);

        return createExchangeResponse(savedConversion);
    }

    private ExchangeResponse createExchangeResponse(CurrencyConversion conversion) {
        ExchangeResponse response = new ExchangeResponse();
        response.setTransactionID(conversion.getTransactionID()); // Usando o ID gerado automaticamente
        response.setUserID(conversion.getUserID());
        response.setFromCurrency(conversion.getFromCurrency());
        response.setToCurrency(conversion.getToCurrency());
        response.setFromValue(conversion.getFromValue());
        response.setToValue(conversion.getToValue());
        response.setExchangeRate(conversion.getExchangeRate());
        response.setDateTime(conversion.getDateTime());
        return response;
    }

    private CurrencyConversion createCurrencyConversion(ExchangeRequest request, BigDecimal exchangeRate, BigDecimal toValue) {
        CurrencyConversion conversion = new CurrencyConversion();
        conversion.setUserID(request.getUserID());
        conversion.setFromCurrency(request.getFromCurrency());
        conversion.setToCurrency(request.getToCurrency());
        conversion.setFromValue(request.getValue());
        conversion.setToValue(toValue);
        conversion.setExchangeRate(exchangeRate);
        conversion.setDateTime(LocalDateTime.now());
        return conversion;
    }

    public List<CurrencyConversion> findExchangeRecordsByUser(Long userID) {
        return currencyRepository.findByUserID(userID);
    }

    public List<CurrencyConversion> findAllRecords() {
        return currencyRepository.findAll();
    }
}
