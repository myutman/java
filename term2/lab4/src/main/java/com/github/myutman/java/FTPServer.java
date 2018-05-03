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

/**
 * Server that can conduct two operations, to give a list of all the files in  given directory and to give a file at given path.
 */
public class FTPServer {

    private static final Type GSON_MAP_TYPE = new TypeToken<Map<String, Object>>(){}.getType();

    /**
     * Logic of processing queries.
     * @param socket client to talk to
     */
    public static void serverLogic(final Socket socket) throws IOException {
        try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            String data = inputStream.readUTF();
            @NotNull Map<String, Object> map = new Gson().fromJson(data, GSON_MAP_TYPE);
            int type = (int) Math.round((double) map.get("type"));
            @NotNull String path = (String) map.get("path");
            System.err.println("type: " + type + ", path: " + path);
            File file = new File(path);
            if (type == 1) {
                Map<String, Object> outMap = new HashMap<>();
                if (!file.exists() || !file.isDirectory()) {
                    outMap.put("size", 0);
                    outMap.put("list", Collections.emptyList());
                } else {
                    @NotNull String[] fileList = file.list();
                    outMap.put("size", fileList.length);
                    List<Map<String, Object>> list = new ArrayList<>();
                    for (@NotNull String fileName : fileList) {
                        File file1 = new File(file.getAbsolutePath() + File.separator + fileName);
                        Map<String, Object> smallMap = new HashMap<>();
                        smallMap.put("name", fileName);
                        smallMap.put("is_dir", file1.isDirectory());
                        list.add(smallMap);
                    }
                    outMap.put("list", list);
                }
                data = new Gson().toJson(outMap, GSON_MAP_TYPE);
                outputStream.writeUTF(data);
                System.err.println("Written data: " + data);
                } else if (type == 2) {
                if (!file.exists() || file.isDirectory()) {
                    outputStream.writeUTF("0");
                } else {
                    long size = file.length();
                    outputStream.writeUTF(Long.toString(size));
                    byte[] buffer = new byte[1024];
                    try (FileInputStream inputStream1 = new FileInputStream(file)) {
                        while (size > 0) {
                            int sz = inputStream1.read(buffer);
                            outputStream.write(buffer, 0, sz);
                            size -= sz;
                        }
                    }
                }
            } else {
                System.err.println("lol");
                System.exit(0);
            }
        } finally {
            socket.close();
        }
    }

    /**
     * Runs the server.
     * @param args port (default 6666)
     */
    public static void main(String[] args) {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
            System.err.println(port);
        } else {
            port = 6666;
        }
        try (final ServerSocket serverSocket = new ServerSocket(port)) {
            Executor executor = Executors.newSingleThreadExecutor();
            while (true) {
                final Socket socket = serverSocket.accept();
                executor.execute(() -> {
                    try {
                        serverLogic(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
