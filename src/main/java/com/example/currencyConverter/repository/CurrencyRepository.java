package com.example.currencyConverter.repository;

import com.example.currencyConverter.model.CurrencyConversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<CurrencyConversion, Long> {
}
