package com.github.julesrb.trademocksystem.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
// TODO: use these annotations not to write yourself default methods
//@Data // generates getters, setters, toString, equals, hashCode
//@NoArgsConstructor // generates a no-args constructor (needed by JPA)
//@AllArgsConstructor // generates a constructor with all fields
public class BuyingPowerEntity {
    @Id
    private String portfolioId;
    private BigDecimal amount;

    public BuyingPowerEntity(String portfolioId, BigDecimal amount) {
        this.portfolioId = portfolioId;
        this.amount = amount;
    }

    public BuyingPowerEntity() {
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}