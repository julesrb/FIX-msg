package com.github.julesrb.fixme.market;

import com.github.julesrb.fixme.common.NonBlockingPortsListen;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello and welcome from BROKER!");
        try {
            NonBlockingPortsListen servers = new NonBlockingPortsListen();
            int assignedPort = servers.addPort(0); // OS choose free port

            while (true) {
                servers.poll();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}