package controller;

import model.user.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;

public class AppClient {
    public static Socket socket;
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;
    public static ObjectInputStream objectInputStream;
    public static String token;

    public static void initializeNetwork() {
        try {
            socket = new Socket("localhost", 8585);
            token = "";
            dataInputStream = new DataInputStream(socket.getInputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException x) {
            x.printStackTrace();
        }
    }

    public static void setToken(String token) {
        AppClient.token = token;
    }

    public static void deleteToken(){
        token = "";
    }

    public static String getToken() {
        return token;
    }
}
