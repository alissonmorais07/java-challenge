package com.example.currencyConverter.repository;

import com.example.currencyConverter.model.CurrencyConversion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<CurrencyConversion, Long> {
    List<CurrencyConversion> findByUserID(Long userID);
}
