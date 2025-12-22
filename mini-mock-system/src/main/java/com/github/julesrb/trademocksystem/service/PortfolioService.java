package com.github.julesrb.trademocksystem.service;

import com.github.julesrb.trademocksystem.OrderSide;
import com.github.julesrb.trademocksystem.repository.*;
import com.github.julesrb.trademocksystem.exception.InsufficientFundsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class PortfolioService {

    private final BuyingPowerRepository buyingPowerRepository;
    private final InventoryRepository inventoryRepository;

    PortfolioService(BuyingPowerRepository buyingPowerRepository, InventoryRepository inventoryRepository) {
        this.buyingPowerRepository = buyingPowerRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public BigDecimal validateAndCalculateBuyingPower(String portfolioId, OrderSide side, BigDecimal quantity, BigDecimal price) {
        BuyingPowerEntity buyingPower = buyingPowerRepository.findById(portfolioId)
                .orElse(new BuyingPowerEntity(portfolioId, new BigDecimal("5000.00")));

        BigDecimal totalPrice = quantity.multiply(price);
        BigDecimal currentAmount = buyingPower.getAmount();
        BigDecimal newBuyingPowerAmount;

        if (side == OrderSide.BUY) {
            newBuyingPowerAmount = currentAmount.subtract(totalPrice);
            if (newBuyingPowerAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientFundsException("Not enough buying power");
            }
        } else { // SELL
            newBuyingPowerAmount = currentAmount.add(totalPrice);
        }
        return newBuyingPowerAmount;
    }

    public BigDecimal calculateRollbackBuyingPower(String portfolioId, OrderSide side, BigDecimal quantity, BigDecimal price) {
        BuyingPowerEntity buyingPower = buyingPowerRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("buying power not found"));

        BigDecimal totalPrice = quantity.multiply(price);
        BigDecimal currentAmount = buyingPower.getAmount();

        if (side == OrderSide.BUY) {
            return currentAmount.add(totalPrice);
        } else {
            return currentAmount.subtract(totalPrice);
        }
    }

    public BigDecimal validateAndCalculateInventory(String portfolioId, String isin, OrderSide side, BigDecimal quantity) {
        InventoryEntity inventory = inventoryRepository.findById(new InventoryEntityId(portfolioId, isin))
                .orElse(new InventoryEntity(portfolioId, isin, BigDecimal.ZERO));

        BigDecimal currentQuantity = inventory.getQuantity();
        BigDecimal newQuantity;

        if (side == OrderSide.SELL) {
            newQuantity = currentQuantity.subtract(quantity);
            if (newQuantity.compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientFundsException("Insufficient inventory");
            }
        } else { // BUY
            newQuantity = currentQuantity.add(quantity);
        }
        return newQuantity;
    }


    public BigDecimal calculateRollbackInventory(String portfolioId, String isin, OrderSide side, BigDecimal quantity) {
        InventoryEntity inventory = inventoryRepository.findById(new InventoryEntityId(portfolioId, isin))
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found"));

        BigDecimal currentQuantity = inventory.getQuantity();

        if (side == OrderSide.BUY) {
            return currentQuantity.subtract(quantity);
        } else { // SELL
            return currentQuantity.add(quantity);
        }
    }

    public void updateBuyingPower(String portfolioId, BigDecimal newAmount) {
        BuyingPowerEntity buyingPower = new BuyingPowerEntity(portfolioId, newAmount);
        buyingPowerRepository.save(buyingPower);
    }

    public void updateInventory(String portfolioId, String isin, BigDecimal quantity) {
        InventoryEntity inventoryEntity = new InventoryEntity(portfolioId, isin, quantity);
        inventoryRepository.save(inventoryEntity);
    }
}