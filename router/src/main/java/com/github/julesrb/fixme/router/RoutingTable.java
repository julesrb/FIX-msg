package com.github.julesrb.fixme.router;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class RoutingTable {

    private static RoutingTable instance;
    private int routingIdMarketCounter = 100000;
    private int routingIdBrokerCounter = 200000;
    Map<Integer, ConnectionContext> routerMap = new HashMap<>();

public RoutingTable() {}

    public RoutingTable getInstance() {
        if (instance == null) {
            instance = new RoutingTable();
        }
        return instance;
    }

    int addBroker(SocketChannel client) {
        routerMap.put(routingIdBrokerCounter, new ConnectionContext(client));
        return routingIdBrokerCounter++;
    }

    int addMarket(SocketChannel client) {
        routerMap.put(routingIdMarketCounter, new ConnectionContext(client));
        return routingIdMarketCounter++;
    }

    //TODO remove entry from the map if the connection closes ?




}
