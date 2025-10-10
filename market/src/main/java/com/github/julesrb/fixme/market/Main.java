package com.github.julesrb.fixme.market;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello and welcome from MARKET!");
        Market market = null;
        try {
            for (int i = 0; i < 10; i++) {
                market = new Market();
                market.connect();
            }
            market = new Market();
            market.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        market.pollLoop();
    }
}