package com.github.myutman.java;

import com.github.myutman.java.MyArrayProtos.MyArray;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadWriteServer implements Runnable {

    private int limit;
    private int answered = 0;
    private Socket setSocket = null;

    ReadWriteServer() {
        limit = -1;
    }

    ReadWriteServer(int limit, Socket setSocket) {
        this.limit = limit;
        this.setSocket = setSocket;
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
        ExecutorService pool = Executors.newCachedThreadPool();
        int ct = 0;
        if (setSocket != null) {
            try {
                RequestProtos.Request.newBuilder()
                        .setLimit(0)
                        .setType(0)
                        .build()
                        .writeDelimitedTo(setSocket.getOutputStream());
            } catch (IOException e) {
                return;
            }
        }
        while (limit == -1 || ct < limit) {
            ExecutorService sender = Executors.newSingleThreadExecutor();
            try {
                final Socket socket = serverSocket.accept();
                ct++;
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
                    pool.submit(() -> {
                        MyArray newArray = Utils.sort(array);
                        sender.submit(() -> {
                            try (OutputStream outputStream = socket.getOutputStream()) {
                                Utils.processWriting(newArray, outputStream);
                            } catch (IOException ignored) {
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
                    });

                });
                thread.setDaemon(true);
                thread.start();
            } catch (IOException ignored) {
                System.err.println("hell");
            }
        }
    }
}
