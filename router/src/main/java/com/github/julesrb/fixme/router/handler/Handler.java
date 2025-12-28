package com.github.julesrb.fixme.router.handler;

import com.github.julesrb.fixme.common.FixMessage;

abstract class Handler {
    protected Handler next;

    private void setNext(Handler handler) {
        this.next = handler;
    }

    public abstract void handle(String rawData, FixMessage msg);
}