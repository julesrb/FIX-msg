package com.github.julesrb.trademocksystem.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class MarketDataService {

    public BigDecimal getPrice(String isin) {
        return switch (isin) {
            case "US67066G1040" -> new BigDecimal("100.00");
            case "US0378331005" -> new BigDecimal("200.00");
            case "US5949181045" -> new BigDecimal("35.50");
            default -> throw new IllegalArgumentException("Unknown ISIN: " + isin);
        };
    }
}