package com.github.julesrb.trademocksystem.dto;

import com.github.julesrb.trademocksystem.OrderSide;
import com.github.julesrb.trademocksystem.OrderStatus;

import java.math.BigDecimal;

public class OrderDTOResponse {
    private Long id;
    private String portfolioId;
    private String isin;
    private OrderSide side;
    private BigDecimal quantity;
    private OrderStatus status;

    // Constructor for cancelOrder method (without side and quantity)
    public OrderDTOResponse(Long id, String portfolioId, String isin, OrderStatus status) {
        this.id = id;
        this.portfolioId = portfolioId;
        this.isin = isin;
        this.status = status;
    }

    // Constructor for getOrder method (with all fields)
    public OrderDTOResponse(Long id, String portfolioId, String isin, OrderSide side, BigDecimal quantity, OrderStatus status) {
        this.id = id;
        this.portfolioId = portfolioId;
        this.isin = isin;
        this.side = side;
        this.quantity = quantity;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}