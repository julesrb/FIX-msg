package com.github.julesrb.fixme.broker;

import java.io.IOException;

public class Main {
        public static void main(String[] args) {

            System.out.println("Hello and welcome from MARKET!");
            Broker broker = null;
            try {
                for (int i = 0; i < 10; i++) {
                    broker = new Broker();
                    broker.connect();
                }
                broker = new Broker();
                broker.connect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            broker.pollLoop();
    }
}