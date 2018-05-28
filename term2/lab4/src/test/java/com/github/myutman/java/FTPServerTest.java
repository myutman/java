package com.github.myutman.java;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class FTPServerTest {

    private ServerSocket serverSocket;
    private Socket socketOfClient;
    private Socket socketOfServer;
    private final String PATH = "./src/test/resources";
    private Thread thread;

    @Before
    public void before() throws IOException {
        serverSocket = new ServerSocket(9323);
        socketOfClient = new Socket("127.0.0.1", 9323);
        socketOfServer = serverSocket.accept();
        thread = new Thread(() -> {
            try {
                FTPServer.serverLogic(socketOfServer);
            } catch (IOException ignored) { }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @After
    public void after() throws IOException, InterruptedException {
        serverSocket.close();
        socketOfClient.close();
        socketOfServer.close();
    }

    @Test
    public void testListEmptyDir() {
        List ans = FTPClient.list(socketOfClient, PATH + File.separator + "empty");
        assertEquals(Collections.EMPTY_LIST, ans);
    }

    @Test
    public void testListNotEmptyDir() {
        List ans = FTPClient.list(socketOfClient, PATH + File.separator + "not_empty");
        Map<String, Object> smallMap = new HashMap<>();
        smallMap.put("is_dir", false);
        smallMap.put("name", "input.txt");
        assertEquals(Collections.singletonList(smallMap), ans);
    }

    @Test
    public void testListNotDir() {
        List ans = FTPClient.list(socketOfClient, PATH + File.separator + "not_dir.txt");
        assertEquals(Collections.EMPTY_LIST, ans);
    }

    @Test
    public void testListNotExists() {
        List ans = FTPClient.list(socketOfClient, PATH + File.separator + "not_exist");
        assertEquals(Collections.EMPTY_LIST, ans);
    }

    @Test
    public void testGetNotDir() throws IOException {
        File newFile = new File(PATH + File.separator + "new_dir/new_file.dat");
        newFile.getParentFile().mkdirs();
        final byte[] data = new byte[]{78, 19, 23, 9};
        try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
            outputStream.write(data);
        }
        File file = new File( "." + File.separator + "new_dir/new_file.dat");
        file.getParentFile().mkdirs();
        assertTrue(FTPClient.get(socketOfClient, PATH + File.separator + "new_dir/new_file.dat", "." + File.separator + "new_dir/new_file.dat"));
        assertEquals(4, file.length());
        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[4];
            inputStream.read(buffer);
            for (int i = 0; i < 4; i++) {
                assertEquals(data[i], buffer[i]);
            }
        }
    }

    @Test
    public void testGetDir() {
        assertFalse(FTPClient.get(socketOfClient, PATH + File.separator + "not_empty", "." + File.separator + "not_empty"));
    }

    @Test
    public void testGetNotExists() {
        assertFalse(FTPClient.get(socketOfClient, PATH + File.separator + "not_exists", "." + File.separator + "not_exists"));
    }
}