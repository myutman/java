package com.github.myutman.java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.NotNull;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FTPServer {

    private static final Type GSON_MAP_TYPE = new TypeToken<Map<String, Object>>(){}.getType();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(6666)) {
            Executor executor = Executors.newSingleThreadExecutor();
            while (true) {
                try (final Socket socket = serverSocket.accept()) {
                    executor.execute(() -> {
                        try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())){
                            @NotNull Map<String, Object> map = new Gson().fromJson(inputStream.readUTF(), GSON_MAP_TYPE);
                            @NotNull Object type = map.get("type");
                            @NotNull String path = (String) map.get("path");
                            File file = new File(path);
                            Map<String, Object> outMap = new HashMap<>();
                            if (type.equals(1)) {
                                if (!file.exists() || !file.isDirectory()) {
                                    outMap.put("size", 0);
                                    outMap.put("list", Collections.emptyList());
                                } else {
                                    @NotNull String[] fileList = file.list();
                                    outMap.put("size", fileList.length);
                                    List<Map<String, Object>> list = new ArrayList<>();
                                    for (@NotNull String fileName : fileList) {
                                        File file1 = new File(file.getAbsolutePath() + File.pathSeparator + fileName);
                                        Map<String, Object> smallMap = new HashMap<>();
                                        smallMap.put("name", fileName);
                                        smallMap.put("is_dir", file1.isDirectory());
                                        list.add(smallMap);
                                    }
                                    outMap.put("list", list);
                                }
                            } else if (type.equals(2)) {
                                if (!file.exists() || file.isDirectory()) {
                                    outMap.put("size", 0);
                                    outMap.put("content", new byte[]{});
                                } else {
                                    outMap.put("size", file.length());
                                    byte[] buffer = new byte[(int) file.length()];
                                    try (FileInputStream inputStream1 = new FileInputStream(file)) {
                                        inputStream1.read(buffer);
                                        outMap.put("content", buffer);
                                    }
                                }
                            }
                            outputStream.writeUTF(new Gson().toJson(outMap, GSON_MAP_TYPE));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
