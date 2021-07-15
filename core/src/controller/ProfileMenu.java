package controller;

import model.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.TerminalOutput;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProfileMenu {
    public JSONParser parser = new JSONParser();
    private User currentUser;

    public ProfileMenu(String username) {
        currentUser = User.getUserByUsername(username);
    }

    public String profileChangeNickname(String nickname) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "changeNickname");
        jsonObject.put("nickname", nickname);
        jsonObject.put("token",AppClient.getToken());
        String result = "";
        try {
            AppClient.dataOutputStream.writeUTF(jsonObject.toJSONString());
            AppClient.dataOutputStream.flush();
            result = AppClient.dataInputStream.readUTF();
            JSONObject jsonInput = (JSONObject) parser.parse(result);
            if (jsonInput.get("type").equals("Successful")){
                return "success";
            }
            else {
                return "error";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public String profileChangePassword(String currentPassword, String newPassword) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "changePassword");
        jsonObject.put("currentPassword", currentPassword);
        jsonObject.put("newPassword",newPassword);
        jsonObject.put("token",AppClient.getToken());
        String result = "";
        try {
            AppClient.dataOutputStream.writeUTF(jsonObject.toJSONString());
            AppClient.dataOutputStream.flush();
            result = AppClient.dataInputStream.readUTF();
            JSONObject jsonInput = (JSONObject) parser.parse(result);
            if (jsonInput.get("type").equals("Successful")){
                return "success";
            }
            else {
                return jsonInput.get("message").toString();
            }
        } catch (Exception e) {
            return "";
        }
    }

    public boolean canChangeNickname(String nickname) {
        return User.getUserByNickname(nickname) == null;
    }

    public boolean isPasswordWrong(String password) {
        return !currentUser.getPassword().equals(password);
    }

}
