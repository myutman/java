package com.github.myutman.java;

import com.github.myutman.java.MyArrayProtos.MyArray;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Utils {

    @NotNull
    public static MyArray sort(@NotNull MyArray array) {
        MyArray.Builder builder = array.toBuilder();
        int n = array.getSize();
        long start = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (builder.getBase(i) > builder.getBase(j)) {
                    int t = builder.getBase(i);
                    builder.setBase(i, builder.getBase(j));
                    builder.setBase(j, t);
                }
            }
        }
        builder.setQueryTime(System.currentTimeMillis() - start);
        return builder.build();
    }

    @Nullable
    public static MyArray processReading(InputStream inputStream) {
        MyArray array;
        try {
            array = MyArray.parseDelimitedFrom(inputStream);
        } catch (IOException e) {
            System.err.println("Cannot parse.");
            return null;
        }
        if (array.getSize() != array.getBaseCount()) {
            System.err.println("Invalid array.");
            return null;
        }
        return array.toBuilder().setClientTime(System.currentTimeMillis()).build();
    }

    public static void processWriting(MyArray array, OutputStream outputStream) throws IOException{
        MyArray.Builder builder = array.toBuilder();
        builder.setClientTime(System.currentTimeMillis() - builder.getClientTime());
        builder.build().writeDelimitedTo(outputStream);
        outputStream.flush();
    }

    public static void response(Socket setSocket) throws IOException {
        setSocket.getOutputStream().write(0);
        setSocket.getOutputStream().flush();
        setSocket.close();
    }
}