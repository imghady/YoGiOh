package controller;

import org.json.simple.JSONObject;

import java.io.IOException;

public class Logout {

    public static void logout(String username){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "logout");
        jsonObject.put("token",AppClient.getToken());
        jsonObject.put("username",username);
        try {
            AppClient.dataOutputStream.writeUTF(jsonObject.toJSONString());
            AppClient.dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String result = AppClient.dataInputStream.readUTF();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AppClient.deleteToken();
    }

}
