package com.github.julesrb.fixme.router;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hello and welcome from router!");

        NonBlockingPortsListen Servers = new NonBlockingPortsListen();
        Servers.addPort(5000);
        Servers.addPort(5001);

        while (true) {
            Servers.poll();
        }
    }
}