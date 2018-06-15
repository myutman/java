package com.github.myutman.java;

import com.github.myutman.java.MyArrayProtos.MyArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class SimpleServer implements Runnable {

    private int limit;
    private int answered = 0;

    public SimpleServer() {
        this.limit = -1;
    }

    public SimpleServer(int limit) {
        this.limit = limit;
    }

    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(55555);
        } catch (IOException e) {
            System.err.println("Socket is busy");
            return;
        }
        System.out.println(limit);
        int ct = 0;
        while (limit == -1 || ct < limit) {
            Socket socket;
            try {
                socket = serverSocket.accept();
                ct++;
            } catch (IOException e) {
                System.err.println("Connection lost");
                break;
            }
            Thread thread = new Thread(() -> {
                MyArray array;
                try {
                    InputStream inputStream = socket.getInputStream();
                    array = Utils.processReading(inputStream);
                    if (array == null) throw new IOException();
                } catch (IOException ignored) {
                    try {
                        socket.close();
                        return;
                    } catch (IOException ignored1) {
                        return;
                    }
                }
                array = Utils.sort(array);
                OutputStream outputStream;
                try {
                    outputStream = socket.getOutputStream();
                    Utils.processWriting(array, outputStream);
                } catch (IOException e) {
                    System.err.println("Cannot write to ouput stream");
                }
                synchronized (this) {
                    answered++;
                    if (answered == limit) {
                        while (!serverSocket.isClosed()) {
                            try {
                                serverSocket.close();
                            } catch (IOException ignored) {

                            }
                        }
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        }
    }
}
