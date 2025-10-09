package com.github.julesrb.fixme.router;

import com.github.julesrb.fixme.common.NonBlockingPortsListen;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hello and welcome from ROUTER!");
        try {
            NonBlockingPortsListen Servers = new NonBlockingPortsListen();
            Servers.addPort(6000);
            Servers.addPort(5001);

            while (true) {
                Servers.poll();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}