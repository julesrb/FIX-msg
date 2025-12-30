package com.github.julesrb.fixme.common;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NIoClientTest {

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
    public void testBuildFixMsgWorks1() {
        try {
            NioClient cli = new NioClient(5000);
            cli.buildFIXMsg("8=FIX.4.4\u00019=65\u000135=5");
            cli.buildFIXMsg("\u000134=1\u000149=BROKER01" +
                    "\u000156=MARKET01\u000111=ORDER123\u000155=AAPL\u000154=1\u000138=100" +
                    "\u000144=150.50\u000110=07");
            assertTrue(cli.buildFIXMsg("2\u0001"), "A possible FIX msg should be recognized");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Test if FIX stream get recognized as message")
    public void testBuildFixMsgWorks2() {
        try {
            NioClient cli = new NioClient(5000);
            cli.buildFIXMsg("574986\u00018=FIX.4.4\u00019=65\u000135=5");
            cli.buildFIXMsg("\u000134=1\u000149=BROKER01" +
                    "\u000156=MARKET01\u000111=ORDER123\u000155=AAPL\u000154=1\u000138=100" +
                    "\u000144=150.50\u000110=07");
            assertTrue(cli.buildFIXMsg("2\u0001"), "A possible FIX msg should be recognized");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Test if wrong FIX stream get recognized as message")
    public void testBuildFixMsgFails() {
        try {
            NioClient cli = new NioClient(5000);
            cli.buildFIXMsg("\u000134=1\u000149=BROKER01" +
                    "\u000156=MARKET01\u000111=ORDER123\u000155=AAPL\u000154=1\u000138=100" +
                    "\u000144=150.50\u000110=07");
            cli.buildFIXMsg("8=FIX.4.4\u00019=65\u000135=5");
            assertFalse(cli.buildFIXMsg("2\u0001"), "This should ne be seen as a possible FIX msg");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
