package com.example.currencyConverter.repository;

import com.example.currencyConverter.model.CurrencyConversion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CurrencyRepositoryTest {

    @Autowired
    private CurrencyRepository currencyRepository;

    @BeforeEach
    void setUp() {
        CurrencyConversion conversion1 = new CurrencyConversion();
        conversion1.setUserID(1L);
        conversion1.setFromCurrency("USD");
        conversion1.setToCurrency("BRL");
        conversion1.setFromValue(new BigDecimal("100"));
        conversion1.setToValue(new BigDecimal("500"));
        conversion1.setExchangeRate(new BigDecimal("5"));
        conversion1.setDateTime(LocalDateTime.now());

        CurrencyConversion conversion2 = new CurrencyConversion();
        conversion2.setUserID(2L);
        conversion2.setFromCurrency("EUR");
        conversion2.setToCurrency("USD");
        conversion2.setFromValue(new BigDecimal("200"));
        conversion2.setToValue(new BigDecimal("220"));
        conversion2.setExchangeRate(new BigDecimal("1.1"));
        conversion2.setDateTime(LocalDateTime.now());

        currencyRepository.save(conversion1);
        currencyRepository.save(conversion2);
    }

    @Test
    @DisplayName("Should return conversion for user ")
    void findByUserIDCase1() {

        List<CurrencyConversion> conversions = currencyRepository.findByUserID(1L);

        assertNotNull(conversions);
        assertEquals(1, conversions.size());
        assertEquals(1L, conversions.get(0).getUserID());
        assertEquals("USD", conversions.get(0).getFromCurrency());
        assertEquals("BRL", conversions.get(0).getToCurrency());
    }

    @Test
    @DisplayName("Should return empty list for non Existen User")
    void findByUserCase2() {
        List<CurrencyConversion> conversions = currencyRepository.findByUserID(99L);

        assertTrue(conversions.isEmpty());
    }

    @Test
    @DisplayName("Should return all conversions")
    void findAll() {
        List<CurrencyConversion> conversions = currencyRepository.findAll();

        assertNotNull(conversions);
        assertEquals(2, conversions.size());
    }
}