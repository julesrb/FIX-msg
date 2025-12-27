package com.github.julesrb.fixme.common;

public class FixParseException extends Exception {
    public FixParseException(String message) {
        super("FIX parsing error: " + message);
    }
}