package com.github.julesrb.fixme.common;

public class FixValidationException extends RuntimeException {
    public FixValidationException(String message)
    {
        super("Invalid Fix message: " + message);
    }
}
