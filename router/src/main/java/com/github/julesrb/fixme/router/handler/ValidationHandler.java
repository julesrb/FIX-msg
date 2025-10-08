package com.github.julesrb.fixme.router.handler;

import com.github.julesrb.fixme.common.FixMessage;

public class ValidationHandler extends Handler{

    private FixMessage fixMessage;

    public ValidationHandler(FixMessage fixMessage) {
        this.fixMessage = fixMessage;
    }


    @Override
    public void handle(String request) {
        if (fixMessage.validateMessage() && next != null) {
            next.handle(request);
        }
        else {
            System.out.println("Fix message non non validated");
        }
    }
}
