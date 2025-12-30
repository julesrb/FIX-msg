package com.github.julesrb.fixme.common;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class NIOClientTest {

    @Test
    @DisplayName("Should instance a new client without problem")
    public void testNewClient() {
        assertDoesNotThrow(() -> new NioClient(5000));
        assertDoesNotThrow(() -> new NioClient(5001));
        assertDoesNotThrow(() -> new NioClient(5002));
        assertDoesNotThrow(() -> new NioClient(5003));
    }

    //TODO
    @Test
    @DisplayName("Test if connection works if router is up")
    public void testRouterConnection() {
    }

    //TODO
    @Test
    @DisplayName("Test if connection fails if router is not there")
    public void testRouterConnectionless() {
    }


    @Test
    @DisplayName("Test if FIX stream get recognized as message")
    public void testTCPParsingWorks() {
        NIOCleint
    }

    @Test
    @DisplayName("Test if wrong FIX stream get recognized as message")
    public void testTCPParsingfails() {
    }
}
