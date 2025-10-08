package com.github.julesrb.fixme.router.handler;

abstract class Handler {
    protected Handler next;

    public void setNext(Handler handler) {
        this.next = handler;
    }

    public abstract void handle(String request);
}