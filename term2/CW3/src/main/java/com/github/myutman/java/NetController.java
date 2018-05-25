package com.github.myutman.java;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetController extends Controller {

    private String ip;
    private DataOutputStream dos;
    private Socket socket;

    @Override
    public Socket getSocket() {
        socket = null;
        while (socket == null) {
            try {
                socket = new Socket(ip, 8888);
            } catch (IOException e) {
                socket = null;
            }
        }
        return socket;
    }

    @Override
    public void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NetController(String ip) {
        this.ip = ip;
    }

    @Override
    public void set(int i, int j, int side) {
        try {
            dos = new DataOutputStream(getSocket().getOutputStream());
        } catch (IOException e) {
            System.err.println("error");
        }
        super.set(i, j, side);
        try {
            dos.writeUTF(i + " " + j);
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeSocket();
    }
}
