package com.github.myutman.java;

import com.sun.istack.internal.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkPlayer implements Player {

    //private Socket socket;
    //private DataInputStream dis;
    //private DataOutputStream dos;

    /*public Socket getSocket() {
        return socket;
    }*/

    @Override
    public void makeTurn(Controller controller) {
        String request = null;
        DataInputStream dis = null;
        @NotNull Socket socket = controller.getSocket();
        try {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (dis == null) return;
        try {
            request = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (request == null) return;
        String coord[] = request.split(" ");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);
        controller.set(x, y, controller.getTurn());
        controller.changeTurn();
        controller.closeSocket();
    }
}
