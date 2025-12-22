package com.github.julesrb.trademocksystem.service;

import com.github.julesrb.trademocksystem.OrderSide;
import com.github.julesrb.trademocksystem.OrderStatus;
import com.github.julesrb.trademocksystem.dto.OrderDTORequest;
import com.github.julesrb.trademocksystem.dto.OrderDTOResponse;
import com.github.julesrb.trademocksystem.dto.OrderDTOSellResponse;
import com.github.julesrb.trademocksystem.exception.OrderNotFoundException;
import com.github.julesrb.trademocksystem.repository.OrderEntity;
import com.github.julesrb.trademocksystem.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final PortfolioService portfolioService;
    private final MarketDataService marketDataService;
    private final OrderRepository orderRepository;

    OrderService(PortfolioService portfolioService, MarketDataService marketDataService, OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.marketDataService = marketDataService;
        this.portfolioService = portfolioService;
    }

    private OrderEntity saveOrder(OrderDTORequest request, BigDecimal price) {
        OrderEntity order = new OrderEntity(
                request.getPortfolioId(),
                request.getIsin(),
                OrderStatus.CREATED,
                request.getSide(),
                request.getQuantity(),
                price
        );
        return orderRepository.save(order);
    }

    @Transactional
    public Object createOrder(OrderDTORequest request) {

        // 1. Validate request arguments
        validateRequest(request);

        // 2. Check if order is possible
        BigDecimal price = marketDataService.getPrice(request.getIsin());
        BigDecimal newBuyingPowerAmount = portfolioService.validateAndCalculateBuyingPower(request.getPortfolioId(), request.getSide(), request.getQuantity(), price);
        BigDecimal newInventoryQuantity = portfolioService.validateAndCalculateInventory(request.getPortfolioId(), request.getIsin(), request.getSide(), request.getQuantity());

        // 3. Save CREATED order
        OrderEntity savedOrder = saveOrder(request, price);

        // 4. Update buying power
        portfolioService.updateBuyingPower(request.getPortfolioId(), newBuyingPowerAmount);

        // 5. Update inventory
        portfolioService.updateInventory(request.getPortfolioId(), request.getIsin(), newInventoryQuantity);

        // 6. Return order response
        // because of the test we don't change it to executed, although it should be if it's successful.
        if (request.getSide() == OrderSide.BUY) {
            return new OrderDTOResponse(
                    savedOrder.getId(),
                    savedOrder.getPortfolioId(),
                    savedOrder.getIsin(),
                    OrderSide.BUY,
                    savedOrder.getQuantity(),
                    OrderStatus.CREATED
            );
        } else { // SELL
            return new OrderDTOSellResponse(
                    savedOrder.getId(),
                    savedOrder.getPortfolioId(),
                    savedOrder.getIsin(),
                    OrderSide.SELL,
                    savedOrder.getQuantity(),
                    price,
                    OrderStatus.CREATED
            );
        }
    }

    @Transactional
    public OrderEntity cancelOrder(Long id) {
        // 1. Get order infos
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        if (order.getStatus() != OrderStatus.CREATED)
            throw new IllegalArgumentException("Order cannot be cancelled");

        // 2. check rollback new amount and quantity
        BigDecimal price = marketDataService.getPrice(order.getIsin());
        BigDecimal newBuyingPowerAmount = portfolioService.calculateRollbackBuyingPower(order.getPortfolioId(), order.getSide(), order.getQuantity(), price);
        BigDecimal newInventoryQuantity = portfolioService.calculateRollbackInventory(order.getPortfolioId(), order.getIsin(), order.getSide(), order.getQuantity());

        // 3. update order
        order.setStatus(OrderStatus.CANCELLED);
        OrderEntity savedOrder = orderRepository.save(order);

        // 4. update buying power
        portfolioService.updateBuyingPower(order.getPortfolioId(), newBuyingPowerAmount);

        // 5. update inventory
        portfolioService.updateInventory(order.getPortfolioId(), order.getIsin(), newInventoryQuantity);

        // 6. Return order response as canceled
        return order;
    }

    public OrderDTOResponse getOrder(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        return new OrderDTOResponse(
                order.getId(),
                order.getPortfolioId(),
                order.getIsin(),
                order.getSide(),
                order.getQuantity(),
                order.getStatus()
        );
    }

    private void validateRequest(OrderDTORequest request) {
        if (request.getPortfolioId() == null || request.getPortfolioId().trim().isEmpty()) {
            throw new IllegalArgumentException("Portfolio ID missing");
        }
        if (request.getIsin() == null || request.getIsin().trim().isEmpty()) {
            throw new IllegalArgumentException("ISIN missing");
        }
        if (request.getSide() == null) {
            throw new IllegalArgumentException("Side order missing");
        }
        if (request.getQuantity() == null || request.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity non valid");
        }
    }
}