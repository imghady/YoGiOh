package controller;

import model.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;

public class ChatRoom {
    public JSONParser parser = new JSONParser();

    public void addChat(String username, String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "addChat");
        jsonObject.put("username", username);
        jsonObject.put("message", message);
        try {
            AppClient.dataOutputStream.writeUTF(jsonObject.toJSONString());
            AppClient.dataOutputStream.flush();
            AppClient.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getChat() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "getChat");
        String result = "";
        try {
            AppClient.dataOutputStream.writeUTF(jsonObject.toJSONString());
            AppClient.dataOutputStream.flush();
            result = AppClient.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
