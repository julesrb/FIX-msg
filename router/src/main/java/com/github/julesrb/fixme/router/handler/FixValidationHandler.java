package com.github.julesrb.fixme.router.handler;

import com.github.julesrb.fixme.common.FixMessage;

public class FixValidationHandler extends Handler {

    @Override
    public void handle(String rawData, FixMessage msg) {
        try {
            FixMessage fixMessage = new FixMessage(rawData);
            if (this.next != null) {
                this.next.handle(rawData, fixMessage);
            }
        } catch (Exception e) {
            System.out.println("Router handler failed: " + e.getMessage());
        }
    }
}
