package com.github.myutman.java;

import com.github.myutman.java.MyArrayProtos.MyArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadWriteServer {

    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(55555);
        } catch (IOException e) {
            System.err.println("Socket is busy");
            return;
        }
        ExecutorService pool = Executors.newCachedThreadPool();
        while (true) {
            ExecutorService sender = Executors.newSingleThreadExecutor();
            try {
                final Socket socket = serverSocket.accept();
                Thread thread = new Thread(() -> {
                    try {
                        InputStream inputStream = socket.getInputStream();
                        MyArray array = MyArray.parseDelimitedFrom(inputStream);
                        pool.submit(() -> {
                            int n = array.getSize();
                            int[] toSort = new int[n];
                            for (int i = 0; i < n; i++) {
                                toSort[i] = array.getBase(i);
                            }
                            Arrays.sort(toSort);
                            MyArray.Builder builder = MyArray.newBuilder();
                            builder.setSize(n);
                            for (int i = 0; i < n; i++) {
                                builder.addBase(toSort[i]);
                            }
                            MyArray newArray = builder.build();
                            sender.submit(() -> {
                                try (OutputStream outputStream = socket.getOutputStream()) {
                                    newArray.writeDelimitedTo(outputStream);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                thread.setDaemon(true);
                thread.start();
            } catch (IOException ignored) {
                System.err.println("hell");
            }
        }
    }
}
