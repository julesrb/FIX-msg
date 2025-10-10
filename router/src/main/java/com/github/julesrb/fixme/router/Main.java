package com.github.julesrb.fixme.router;

import com.github.julesrb.fixme.common.NioMultiServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hello and welcome from ROUTER!");
        try {
            NioMultiServer Servers = new NioMultiServer();
            Servers.addPort(5000);
            Servers.addPort(5001);

            while (true) {
                Servers.poll();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}