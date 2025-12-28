package com.github.julesrb.fixme.router;

import com.github.julesrb.fixme.common.NioMultiServer;

import java.io.IOException;

public class Router extends NioMultiServer {

    private final RoutingTable routingTable;

    public Router(RoutingTable routingTable ) throws IOException {
        super();
        this.routingTable = routingTable;
    }
// TODO
//    public routerPoll() {
//        if ( new connection on port 5000) {
//            int brokerID = routingTable.addBroker();
//            connection con = routingTable.routerMap.get(brokerID);
//            con.send(brokerID);
//            if fails -> throw "registration of the new broker failed"
//        }
//        if ( new connection on port 5001) {
//            routingTable.addMarket();
//        }
//    }
}
