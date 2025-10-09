package com.github.julesrb.fixme.common;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FixMessageTest {
    @Test
    public void testFixMessageParsing() throws FixParseException {
        var msg  = new FixMessage("8=FIX.4.4\u00019=65\u000135=5\u000134=1\u000149=BROKER01" +
                "\u000156=MARKET01\u000111=ORDER123\u000155=AAPL\u000154=1\u000138=100" +
                "\u000144=150.50\u000110=072\u0001");
        var missingEqual  = new FixMessage("8=FIX.4.4\u00019=65\u000135=5\u000134=1\u000149=BROKER01" +
                "\u000156=MARKET01\u000111=ORDER123\u000155=AAPL\u000154=1\u000138=100" +
                "\u000144=150.50\u000110072\u0001");
        var manyEquals  = new FixMessage("8=F==hy");
        var empty  = new FixMessage("");

        assertThrows(FixParseException.class, () -> empty.parseMessage());
        assertThrows(FixParseException.class, () -> missingEqual.parseMessage());
        assertThrows(FixParseException.class, () -> manyEquals.parseMessage());
        msg.parseMessage();
    }
}