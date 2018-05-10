package com.github.myutman.java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.NotNull;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.*;

/**
 * Console client that can send queries to server.
 */
public class FTPClient {

    private static final Type GSON_MAP_TYPE = new TypeToken<Map<String, Object>>(){}.getType();

    /**
     * Returns the list of files in given directory.
     * @param socket server to talk to
     * @param path path to the directory
     * @return list of files in directory
     */
    @NotNull
    public static List list(Socket socket, String path) {
        try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            //Output
            Map<String, Object> outMap = new HashMap<>();
            int type = 1;
            outMap.put("type", type);
            outMap.put("path", path);
            String data = new Gson().toJson(outMap, GSON_MAP_TYPE);
            outputStream.writeUTF(data);
            //Input
            data = inputStream.readUTF();
            Map<String, Object> result = new Gson().fromJson(data, GSON_MAP_TYPE);
            return (List) result.get("list");
        } catch (IOException e) {
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * Downloads file from the server and returns its descriptor.
     * @param socket server to talk to
     * @param path path to the file
     * @return new file that is copy of requested
     */
    public static boolean get(Socket socket, String path, String destination) {
        try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {
            //Output
            Map<String, Object> outMap = new HashMap<>();
            int type = 2;
            outMap.put("type", type);
            outMap.put("path", path);
            String data = new Gson().toJson(outMap, GSON_MAP_TYPE);
            outputStream.writeUTF(data);
            //Input
            long size = Long.parseLong(inputStream.readUTF());
            if (size == 0) {
                return false;
            }
            File outputFile = new File(destination);
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[1024];
                while (size > 0) {
                    int sz = inputStream.read(buffer);
                    fileOutputStream.write(buffer, 0, sz);
                    size -= sz;
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Console application that helps to send queries.
     * @param args ip and port (default 127.0.0.1, 6666)
     */
    public static void main(String[] args) {
        String ip;
        int port;
        if (args.length >= 2) {
            ip = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            ip = "127.0.0.1";
            port = 6666;
        }
        String command;
        String path;
        Scanner scanner = new Scanner(System.in);
        command = scanner.next();
        while (!"exit".equals(command)) {
            path = scanner.next();
            try (Socket socket = new Socket(ip, port)) {
                if ("list".equals(command)) {
                    List ans = list(socket, path);
                    for (Object object: ans) {
                        Map<String, Object> map = (Map<String, Object>) object;
                        System.out.printf("(%b, %s) ", map.get("is_dir"), map.get("name"));
                    }
                    System.out.println();
                } else if ("get".equals(command)) {
                    File file = new File("." + File.separator + path);
                    file.getParentFile().mkdirs();
                    get(socket, path, file.getCanonicalPath());
                    if (file != null) {
                        System.out.println(file.getCanonicalPath());
                    } else {
                        System.out.println("null");
                    }
                } else {
                    System.err.println("Incorrect command.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            command = scanner.next();
        }
    }
}
