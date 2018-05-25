package com.github.myutman.java;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetController extends Controller {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public Socket getSocket() {
        return socket;
    }

    public NetController(Socket socket) {
        this.socket = socket;
        try {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("error");
        }
        try {
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("error");
        }
    }

    @Override
    public void set(int i, int j, int side) {
        try {
            dos.writeUTF(i + " " + j);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
