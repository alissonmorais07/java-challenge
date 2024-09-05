package com.example.currencyConverter.service;

import com.example.currencyConverter.dto.request.ExchangeRequest;
import com.example.currencyConverter.dto.response.ExchangeResponse;
import com.example.currencyConverter.model.CurrencyApiResponse;
import com.example.currencyConverter.model.CurrencyConversion;
import com.example.currencyConverter.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CurrencyConversionService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyRepository currencyRepository;

    public ExchangeResponse convertCurrency(ExchangeRequest request) {
        // URL da API com os pares de moedas desejados
        String url = String.format("https://economia.awesomeapi.com.br/json/last/%s-%s",
                request.getFromCurrency(), request.getToCurrency());

        // Obtém a resposta da API
        CurrencyApiResponse apiResponse = restTemplate.getForObject(url, CurrencyApiResponse.class);

        // Verifica se a resposta da API é nula
        if (apiResponse == null) {
            throw new RuntimeException("A resposta da API é nula. Verifique a URL e a disponibilidade do serviço.");
        }

        // Obtém a taxa de câmbio para o par de moedas
        String key = request.getFromCurrency() + request.getToCurrency();
        BigDecimal exchangeRate = apiResponse.getExchangeRate(key);

        if (exchangeRate == null) {
            throw new RuntimeException("Taxa de câmbio não encontrada para o par de moedas: " + key);
        }

        // Calcula o valor convertido
        BigDecimal toValue = request.getValue().multiply(exchangeRate);

        // Prepara a resposta
        ExchangeResponse response = new ExchangeResponse();
        response.setTransactionID(System.currentTimeMillis()); // Simulação de ID de transação
        response.setUserID(request.getUserID());
        response.setFromCurrency(request.getFromCurrency());
        response.setToCurrency(request.getToCurrency());
        response.setFromValue(request.getValue());
        response.setToValue(toValue);
        response.setExchangeRate(exchangeRate);
        response.setDateTime(LocalDateTime.now());

        //Salva a conversão no banco de dados
        CurrencyConversion conversion;
        conversion = new CurrencyConversion();
        conversion.setUserID(request.getUserID());
        conversion.setFromCurrency(request.getFromCurrency());
        conversion.setToCurrency(request.getToCurrency());
        conversion.setFromValue(request.getValue());
        conversion.setToValue(toValue);
        conversion.setExchangeRate(exchangeRate);
        conversion.setDateTime(LocalDateTime.now());

        //Salva a conversão no repositório
        currencyRepository.save(conversion);

        return response;
    }

}