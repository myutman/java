package com.github.myutman.java;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkPlayer extends HumanPlayer {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public Socket getSocket() {
        return socket;
    }

    /**
     * Class constructor.
     *
     * @param side what figures does he play
     */
    public NetworkPlayer(int side, Socket socket) {
        super(side);
        this.socket = socket;
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
    }

    @Override
    public void makeTurn(Controller controller) {
        String ans = null;
        try {
            ans = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (ans != null) {
            if ("finish".equals(ans)) {

            }
        }
    }
}
