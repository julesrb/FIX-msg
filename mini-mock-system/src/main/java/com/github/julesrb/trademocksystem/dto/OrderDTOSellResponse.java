package com.github.julesrb.trademocksystem.dto;

import com.github.julesrb.trademocksystem.OrderSide;
import com.github.julesrb.trademocksystem.OrderStatus;

import java.math.BigDecimal;

public class OrderDTOSellResponse extends OrderDTOResponse {
    private BigDecimal price;

    public OrderDTOSellResponse(Long id, String portfolioId, String isin, OrderSide side, BigDecimal quantity, BigDecimal price, OrderStatus status) {
        super(id, portfolioId, isin, side, quantity, status);
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}