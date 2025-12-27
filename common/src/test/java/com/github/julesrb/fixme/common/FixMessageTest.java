package com.github.julesrb.fixme.common;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class FixMessageTest {

    private final String validRaw = "8=FIX.4.4\u00019=65\u000135=5\u000134=1\u000149=BROKER01" +
            "\u000156=MARKET01\u000111=ORDER123\u000155=AAPL\u000154=1\u000138=100" +
            "\u000144=150.50\u000110=072\u0001";

    private final String refFIX = "8=FIX.4.4\u00019=148\u000135=D\u000134=1080\u000149=TESTBUY1"+
            "\u000152=20180920-18:14:19.508\u000156=TESTSELL1\u000111=636730640278898634\u000115=USD"+
            "\u000121=2\u000138=7000\u000140=1\u000154=1\u000144=150.50\u000155=MSFT\u000160=20180920-18:14:19.492\u000110=043\u0001";

    @Test
    @DisplayName("Should successfully parse a perfectly formatted FIX message")
    public void testValidMessage() {
        assertDoesNotThrow(() -> new FixMessage(refFIX));
    }

    @Test
    @DisplayName("Should throw FixParseException when message is empty or null")
    public void testEmptyMessage() {
        assertThrows(FixParseException.class, () -> new FixMessage(""));
        assertThrows(FixParseException.class, () -> new FixMessage(null));
    }

    @Test
    @DisplayName("Should throw FixParseException when a token is missing the '=' sign")
    public void testMalformedToken() {
        String malformed = "8=FIX.4.4\u000110072\u0001";
        assertThrows(FixParseException.class, () -> new FixMessage(malformed));
    }

    @Test
    @DisplayName("Should throw FixValidationException when checksum is mathematically incorrect")
    public void testInvalidChecksumValue() {
        // Changed 10=161 to 10=999
        String badChecksum = "8=FIX.4.4\u000135=D\u000149=1\u000156=2\u000155=AAPL\u000154=1\u000138=100\u000144=150\u000110=999\u0001";
        assertThrows(FixValidationException.class, () -> new FixMessage(badChecksum));
    }

    @Test
    @DisplayName("Should throw FixValidationException when a mandatory tag (like 35) is missing")
    public void testMissingMandatoryTag() {
        // Missing Tag 35 (MsgType)
        String missingTag = "8=FIX.4.4\u000149=1\u000156=2\u000155=AAPL\u000154=1\u000138=100\u000144=150\u000110=114\u0001";
        assertThrows(FixValidationException.class, () -> new FixMessage(missingTag));
    }

    @Test
    @DisplayName("Should throw FixValidationException when types are wrong (e.g., Side is not 1 or 2)")
    public void testInvalidSideValue() {
        // Side (54) set to '3' (which is invalid in your logic)
        String badSide = "8=FIX.4.4\u000135=D\u000149=1\u000156=2\u000155=AAPL\u000154=3\u000138=100\u000144=150\u000110=163\u0001";
        assertThrows(FixValidationException.class, () -> new FixMessage(badSide));
    }
}