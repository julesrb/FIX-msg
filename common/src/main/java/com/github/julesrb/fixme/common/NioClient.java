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
    private volatile boolean pooling = true;
    private final Selector selector;
    private final int port;
    private String outBuffer;
    private StringBuilder inBuffer;

    public NioClient(int port) throws IOException {
        this.port = port;
        outBuffer = null;
        selector = Selector.open();
        inBuffer = new StringBuilder("");
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

    public void sendMsg(String msg) {
        outBuffer = msg; //TODO implement and should i watch out for concurrency here ?
    }

    public void handleMsg(String msg) {
        System.out.println("need to implement handle Message");
    }

    public boolean buildFIXMsg(String msg) {
        inBuffer.append(msg);
        int fIXStart = inBuffer.indexOf("8=FIX");
        int fixEndA = inBuffer.indexOf("\u000110=", fIXStart + 1);
        int fixEndB = inBuffer.indexOf("\u0001", fixEndA + 1);
        if (fIXStart == -1) {
            inBuffer.setLength(0);
            return false;
        }
        if (!(fixEndA == -1 || fixEndB == -1)) {
            handleMsg(new String(inBuffer));
            inBuffer.setLength(0);
            return true;
        }
        return false;
    }

    private void handleWrite(SelectionKey key) throws IOException {
        if (outBuffer != null) {
            SocketChannel client = (SocketChannel) key.channel();
            client.write(ByteBuffer.wrap(outBuffer.getBytes(StandardCharsets.UTF_8)));
            outBuffer = null;
        }
    }


    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            int bytesRead = client.read(buffer);
            if (bytesRead == -1) {
                client.close();
                key.cancel();
                return;
            }
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String receivedStr = new String(bytes);
            buildFIXMsg(receivedStr);
        } catch (IOException e) {
            client.close();
            key.cancel();
            throw e;
        }
    }

    public void poll() throws IOException {
        int readyCount = selector.select();
        if (readyCount == 0) return;

        SelectionKey key = selector.selectedKeys().iterator().next();
        selector.selectedKeys().clear();

        if (key.isReadable()) {
            handleRead(key);
        }
        if (key.isWritable()) {
              handleWrite(key);
        }
    }

    public void stop() {
        pooling = false;
        selector.wakeup();
    }

    public void pollLoop() {
        try {
            while (pooling) {
                this.poll();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}


