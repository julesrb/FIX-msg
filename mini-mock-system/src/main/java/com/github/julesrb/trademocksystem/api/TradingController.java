package com.github.julesrb.trademocksystem.api;

import com.github.julesrb.trademocksystem.service.OrderService;
import com.github.julesrb.trademocksystem.dto.OrderDTORequest;
import com.github.julesrb.trademocksystem.dto.OrderDTOResponse;
import com.github.julesrb.trademocksystem.repository.OrderEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TradingController {

    private final OrderService orderService;

    public TradingController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<Object> createOrder(@RequestBody OrderDTORequest request) {
        // cannot add @valid because it needs dependency that might not be installed during the tests and not compile.
        Object response = orderService.createOrder(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTOResponse> getOrder(@PathVariable Long id) {
        OrderDTOResponse response = orderService.getOrder(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderEntity> cancelOrder(@PathVariable Long id) {
        OrderEntity response = orderService.cancelOrder(id);
        return ResponseEntity.ok(response);
    }

}
