package com.github.myutman.java;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.github.myutman.java.RequestProtos.Request;

public class ServerSet {

    public static final int START_SIMPLE = 1;
    public static final int START_READ_WRITE = 2;
    public static final int START_NOT_BLOCKING = 3;
    public static final int STOP = 0;

    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(60000);
        } catch (IOException e) {
            return;
        }
        while (true) {
            try (Socket socket = serverSocket.accept()) {
                Request request = Request.parseDelimitedFrom(socket.getInputStream());
                if (request.getType() == START_SIMPLE) {
                    new SimpleServer(request.getLimit(), socket).run();
                } else if (request.getType() == START_READ_WRITE) {
                    new ReadWriteServer(request.getLimit(), socket).run();
                } else if (request.getType() == START_NOT_BLOCKING) {
                    new NotBlockingServer(request.getLimit(), socket).run();
                }
            } catch (IOException ignored) {

            }
        }
    }
}
