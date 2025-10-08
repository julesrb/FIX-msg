package com.github.julesrb.fixme.router;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;

class ConnectionContext {
    final SocketChannel channel;
    final StringBuilder bufferOut = new StringBuilder();
    final StringBuilder bufferIn = new StringBuilder();
    // TODO handle uncomplete buffers

    ConnectionContext(SocketChannel channel) {
        this.channel = channel;
    }
}