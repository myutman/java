package com.github.myutman.java;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController extends Controller {

    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public Socket getSocket() {
        return socket;
    }

    public ServerController() {
        try {
            serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            System.err.println("Error creating server");
        }
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("No one's gonna play with you");
        }
        try {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(() -> {
            while (true) {
                String request = null;
                try {
                    request = dis.readUTF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (request == null) break;
                String coord[] = request.split(" ");
                int x = Integer.parseInt(coord[0]);
                int y = Integer.parseInt(coord[1]);
                set(x, y, getTurn());
                changeTurn();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
