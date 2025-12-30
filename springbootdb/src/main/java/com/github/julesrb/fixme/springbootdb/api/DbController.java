package com.github.julesrb.fixme.springbootdb.api;

import com.github.julesrb.fixme.springbootdb.repository.BrokerEntity;
import com.github.julesrb.fixme.springbootdb.repository.MarketEntity;
import com.github.julesrb.fixme.springbootdb.repository.TransactionEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class DbController {

    public DbController() {
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionEntity> createTransaction(@RequestBody TransactionEntity request) {
       //TODO save transaction
        return ResponseEntity.ok(new TransactionEntity());
    }

    @GetMapping("/market/{id}")
    public ResponseEntity<MarketEntity> postMarket(@PathVariable Long id) {
        //TODO get market
        return ResponseEntity.ok(new MarketEntity());
    }

    @PostMapping("/market/{id}")
    public ResponseEntity<MarketEntity> getMarket(@PathVariable Long id) {
        //TODO save market
        return ResponseEntity.ok(new MarketEntity());
    }

    @GetMapping("/broker/{id}")
    public ResponseEntity<BrokerEntity> postBroker(@PathVariable Long id) {
        //TODO get broker
        return ResponseEntity.ok(new BrokerEntity());
    }

    @PostMapping("/broker/{id}")
    public ResponseEntity<BrokerEntity> getBrocker(@PathVariable Long id) {
        //TODO save broker
        return ResponseEntity.ok(new BrokerEntity());
    }

}
