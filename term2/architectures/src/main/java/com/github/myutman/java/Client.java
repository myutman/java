package com.github.myutman.java;


import com.github.myutman.java.MyArrayProtos.MyArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 55555);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            MyArray array = MyArray.newBuilder()
                    .setSize(3)
                    .addBase(3)
                    .addBase(1)
                    .addBase(2)
                    .build();
            array.writeDelimitedTo(outputStream);
            array = MyArray.parseDelimitedFrom(inputStream);System.out.println(array);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
