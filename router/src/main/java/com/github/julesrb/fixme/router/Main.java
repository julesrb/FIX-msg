package com.github.julesrb.fixme.router;

import com.github.julesrb.fixme.common.NioMultiServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hello and welcome from ROUTER!");
        RoutingTable routingTable = new RoutingTable();
        try {
            Router router = new Router(routingTable);
            router.addPort(5000);
            router.addPort(5001);

            while (true) {
                router.poll();
                //TODO add clean exit ?
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}