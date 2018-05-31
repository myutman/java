package com.github.myutman.java;

import com.github.myutman.java.MyArrayProtos.MyArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class SimpleServer {

    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(55555);
        } catch (IOException e) {
            System.err.println("Socket is busy");
            return;
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(() -> {
                    try (InputStream inputStream = socket.getInputStream();
                         OutputStream outputStream = socket.getOutputStream()) {
                        MyArray array;
                        try {
                            array = MyArray.parseDelimitedFrom(inputStream);
                        } catch (IOException e) {
                            System.err.println("fack");
                            return;
                        }
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
                        array = builder.build();
                        array.writeDelimitedTo(outputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.err.println("back");
                        }
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
