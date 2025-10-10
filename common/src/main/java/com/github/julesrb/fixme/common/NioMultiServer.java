package com.github.julesrb.fixme.common;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class NioMultiServer {

    private int[]       ports;
    private final Selector    selector;

    public NioMultiServer() throws IOException {
        selector = Selector.open();
    }

    public int addPort(int port) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        try {
            server.bind(new InetSocketAddress(port));
        } catch (BindException e) {
            System.err.println("Port " + port + " is already in use!");
        }
        server.register(selector, SelectionKey.OP_ACCEPT);
        int assignedPort = server.socket().getLocalPort();  // <-- actual port assigned
        System.out.println("Socket Server started on port " + assignedPort);
        return assignedPort;
    }

    void acceptConnection(SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel client = server.accept();
        System.out.println("Accepted from port " + ((InetSocketAddress) server.getLocalAddress()).getPort());
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    int readMessage(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        int localPort = ((InetSocketAddress) client.getLocalAddress()).getPort();

        int BUFFER_SIZE = 1024;
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        try {
            int bytesRead = client.read(buffer);
            if (bytesRead == -1) {   // Connection closed by client
                System.out.println("Connection close on port " + localPort);
                key.cancel();
                client.close();
                return -1;
            }
            buffer.flip();
            byte[] receivedBytes = new byte[buffer.remaining()];
            buffer.get(receivedBytes);
            System.out.println(receivedBytes.length);
            String message = new String(receivedBytes, StandardCharsets.UTF_8);
            System.out.println(message);
            // now immediately after receiving the message we want to write the ack to client.
            // So we register OP_WRITE now so that our server is ready to write to outbound buffer.
            key.interestOpsOr(SelectionKey.OP_WRITE);
            return 0;
        } catch (SocketException e) {
            e.printStackTrace();
            key.cancel();
            client.close();
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void poll() throws IOException {
        int readyCount = selector.select();
        if (readyCount == 0) return;

        Set<SelectionKey> readyKeySet = selector.selectedKeys();
        Iterator<SelectionKey> iterator = readyKeySet.iterator();

        while (iterator.hasNext()) {

            SelectionKey key = iterator.next();
            iterator.remove();
            if (key.isAcceptable()) {
                acceptConnection(key);
            }
            if (key.isReadable()) {
                if (readMessage(key) == -1) {
                    continue;
                }
//                Incoming FIX message
//                   ↓
//                concatenate the buffer if needed. might be a fix segement ?
//                   |
//                [ValidationHandler]  → verify checksum, structure
//                   ↓
//                [IdentificationHandler] → find source/destination IDs
//                   ↓
//                [RoutingHandler] → forward to correct Market or Broker
            }
            if (key.isWritable()) {
                SocketChannel client = (SocketChannel) key.channel();
                String response = "server received\n";
                client.write(ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8)));
            }
        }
    }
}
