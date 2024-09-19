package com.example.currencyConverter.service;

import com.example.currencyConverter.dto.request.ExchangeRequest;
import com.example.currencyConverter.dto.response.ExchangeResponse;
import com.example.currencyConverter.model.CurrencyApiResponse;
import com.example.currencyConverter.model.CurrencyConversion;
import com.example.currencyConverter.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CurrencyConversionServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyConversionService currencyConversionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
    }

    @Test
    @DisplayName("Should return exchange response when conversion is successful")
    void convertCurrency() {
        ExchangeRequest request = new ExchangeRequest();
        request.setFromCurrency("USD");
        request.setToCurrency("BRL");
        request.setValue(new BigDecimal("100"));
        request.setUserID(1L);

        String url = "https://economia.awesomeapi.com.br/json/last/USD-BRL";

        CurrencyApiResponse apiResponse = mock(CurrencyApiResponse.class);
        when(apiResponse.getExchangeRate("USDBRL")).thenReturn(new BigDecimal("5.25"));

        CurrencyConversion conversion = new CurrencyConversion();
        conversion.setTransactionID(1L);
        conversion.setUserID(1L);
        conversion.setFromCurrency("USD");
        conversion.setToCurrency("BRL");
        conversion.setFromValue(new BigDecimal("100"));
        conversion.setToValue(new BigDecimal("525"));
        conversion.setExchangeRate(new BigDecimal("5.25"));
        conversion.setDateTime(LocalDateTime.now());

        when(restTemplate.getForObject(url, CurrencyApiResponse.class)).thenReturn(apiResponse);
        when(currencyRepository.save(any(CurrencyConversion.class))).thenReturn(conversion);

        ExchangeResponse response = currencyConversionService.convertCurrency(request);

        assertNotNull(response);
        assertEquals(new BigDecimal("525"), response.getToValue());
        assertEquals(new BigDecimal("5.25"), response.getExchangeRate());
        assertEquals("USD", response.getFromCurrency());
        assertEquals("BRL", response.getToCurrency());
        assertEquals(1L, response.getUserID());
    }

    @Test
    @DisplayName("Should throw RuntimeException when ApiResponse is null ")
    void convertCurrencyCase1() {
        ExchangeRequest request = new ExchangeRequest();
        request.setFromCurrency("USD");
        request.setToCurrency("BRL");
        request.setValue(new BigDecimal("100"));

        String url = "https://economia.awesomeapi.com.br/json/last/USD-BRL";

        when(restTemplate.getForObject(url, CurrencyApiResponse.class)).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            currencyConversionService.convertCurrency(request);
        });

        assertEquals("The API response is null. Check the URL and service availability.", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw RuntimeException when exchange rate is null")
    void convertCurrencyCase2() {
        ExchangeRequest request = new ExchangeRequest();
        request.setFromCurrency("USD");
        request.setToCurrency("BRL");
        request.setValue(new BigDecimal("100"));

        String url = "https://economia.awesomeapi.com.br/json/last/USD-BRL";

        CurrencyApiResponse apiResponse = mock(CurrencyApiResponse.class);

        when(apiResponse.getExchangeRate("USDBRL")).thenReturn(null);

        when(restTemplate.getForObject(url, CurrencyApiResponse.class)).thenReturn(apiResponse);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            currencyConversionService.convertCurrency(request);
        });

        assertEquals("Exchange rate not found for currency pair: USDBRL", exception.getMessage());
    }

    @Test
    @DisplayName("Should return records for given user")
    void findExchangeRecordsByUser() {
        Long userID = 1L;
        CurrencyConversion conversion = new CurrencyConversion();
        conversion.setUserID(userID);

        when(currencyRepository.findByUserID(userID)).thenReturn(List.of(conversion));

        List<CurrencyConversion> records = currencyConversionService.findExchangeRecordsByUser(userID);

        assertNotNull(records);
        assertEquals(1, records.size());
        assertEquals(userID, records.get(0).getUserID());
    }

    @Test
    @DisplayName("Should return all records")
    void findAllRecords() {
        CurrencyConversion conversion = new CurrencyConversion();

        when(currencyRepository.findAll()).thenReturn(List.of(conversion));

        List<CurrencyConversion> records = currencyConversionService.findAllRecords();

        assertNotNull(records);
        assertEquals(1, records.size());
    }
}