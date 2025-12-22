package com.github.julesrb.trademocksystem.dto;

import com.github.julesrb.trademocksystem.OrderSide;

import java.math.BigDecimal;

public class OrderDTORequest {
    private String portfolioId;
    private String isin;
    private OrderSide side;
    private BigDecimal quantity;


    public OrderDTORequest(String portfolioId, String isin, OrderSide side, BigDecimal quantity) {
        this.portfolioId = portfolioId;
        this.isin = isin;
        this.side = side;
        this.quantity = quantity;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public OrderSide getSide() {
        return side;
    }

    public void setSide(OrderSide side) {
        this.side = side;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
