package com.github.myutman.java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FTPClient {

    private static final Type GSON_MAP_TYPE = new TypeToken<Map<String, Object>>(){}.getType();

    public static void main(String[] args) {
        String command;
        String path;
        Scanner scanner = new Scanner(System.in);
        command = scanner.next();
        while ("list".equals(command) || "get".equals(command)) {
            path = scanner.next();

            try (Socket socket = new Socket("192.168.0.48", 6666)) {
                try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                     DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
                    Map<String, Object> outMap = new HashMap<>();
                    outMap.put("type", "list".equals(command) ? 1 : 2);
                    outMap.put("path", path);
                    outputStream.writeUTF(new Gson().toJson(outMap, GSON_MAP_TYPE));
                    System.out.println(inputStream.readUTF());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            command = scanner.next();
        }
    }
}
