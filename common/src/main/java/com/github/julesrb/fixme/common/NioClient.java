package com.github.julesrb.fixme.common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NioClient {
    private SocketChannel channel;
    private final Selector selector;
    private final int port;
    private String outBuffer;

    public NioClient(int port) throws IOException {
        this.port = port;
        outBuffer = null;
        selector = Selector.open();
    }

    public void connect() {
        try {
            SocketChannel clientSocketChannel = SocketChannel.open();
            clientSocketChannel.configureBlocking(false);
            clientSocketChannel.connect(new InetSocketAddress(port));
            while (!clientSocketChannel.finishConnect()) {
            }
            clientSocketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            System.out.println("Client connected to ROUTER");
        } catch (Exception e) {
            System.err.println("Client connection failed: " + e);
        }
    }

    public void sendMessage(String msg) {
        outBuffer = msg; //TODO should i watch out for concurrency here ?
    }

    private void handleWrite(SelectionKey key) throws IOException {
        if (outBuffer != null) {
            SocketChannel client = (SocketChannel) key.channel();
            client.write(ByteBuffer.wrap(outBuffer.getBytes(StandardCharsets.UTF_8)));
            outBuffer = null;
        }
    }

    public void poll() throws IOException {
        int readyCount = selector.select();
        if (readyCount == 0) return;

        SelectionKey key = selector.selectedKeys().iterator().next();
        selector.selectedKeys().clear();

        if (key.isReadable()) {
//            handleRead(key);
        }
        if (key.isWritable()) {
              handleWrite(key);
        }
    }

    public void pollLoop() {
        try {
            while (true) {
                this.poll();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}


