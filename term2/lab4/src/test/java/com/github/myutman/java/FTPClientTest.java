package com.github.myutman.java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FTPClientTest {

    private static final Type GSON_MAP_TYPE = new TypeToken<Map<String, Object>>(){}.getType();

    private ServerSocket serverSocket;
    private Socket socketOfClient;
    private Socket socketOfServer;

    @Before
    public void before() throws IOException {
        serverSocket = new ServerSocket(9323);
        socketOfClient = new Socket("127.0.0.1", 9323);
        socketOfServer = serverSocket.accept();
    }

    @After
    public void after() throws IOException {
        socketOfClient.close();
        socketOfServer.close();
        serverSocket.close();
    }

    @Test
    public void testListEmptyDir() throws IOException {
        final DataOutputStream serverOutputStream = new DataOutputStream(socketOfServer.getOutputStream());
        final DataInputStream serverInputStream = new DataInputStream(socketOfServer.getInputStream());
        Thread thread = new Thread(() -> {
            Map<String, Object> outMap = new HashMap<>();
            outMap.put("size", 0);
            outMap.put("list", Collections.emptyList());
            try {
                serverInputStream.readUTF();
                serverOutputStream.writeUTF(new Gson().toJson(outMap, GSON_MAP_TYPE));
                serverInputStream.close();
                serverOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        List ans = FTPClient.list(socketOfClient, "/");
        assertEquals(Collections.EMPTY_LIST, ans);
    }

    @Test
    public void testListNotEmptyDir() throws IOException {
        final DataOutputStream serverOutputStream = new DataOutputStream(socketOfServer.getOutputStream());
        final DataInputStream serverInputStream = new DataInputStream(socketOfServer.getInputStream());
        final List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> smallMap = new HashMap<>();
        smallMap.put("is_dir", false);
        smallMap.put("name", ".bashrc");
        list.add(smallMap);
        new Thread(() -> {
            Map<String, Object> outMap = new HashMap<>();
            outMap.put("size", 1);
            outMap.put("list", list);
            try {
                serverInputStream.readUTF();
                serverOutputStream.writeUTF(new Gson().toJson(outMap, GSON_MAP_TYPE));
                serverInputStream.close();
                serverOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        List ans = FTPClient.list(socketOfClient, "/");
        assertEquals(list, ans);
    }

    @Test
    public void testGet() throws IOException {
        final DataOutputStream serverOutputStream = new DataOutputStream(socketOfServer.getOutputStream());
        final DataInputStream serverInputStream = new DataInputStream(socketOfServer.getInputStream());
        final byte[] data = new byte[]{78, 19, 23, 9};
        new Thread(() -> {
            try {
                serverInputStream.readUTF();
                serverOutputStream.writeUTF("4");
                serverOutputStream.write(data);
                serverInputStream.close();
                serverOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        File file = FTPClient.get(socketOfClient, ".bashrc");
        assertNotNull(file);
        assertEquals(file.length(), 4);
        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[4];
            inputStream.read(buffer);
            for (int i = 0; i < 4; i++) {
                assertEquals(data[i], buffer[i]);
            }
        }
    }
}