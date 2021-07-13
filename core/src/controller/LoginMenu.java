package controller;

import model.user.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.menu.LoginMenuView;
import view.menu.MainMenuView;

import java.io.IOException;
import java.util.Objects;

public class LoginMenu {
    public JSONParser parser = new JSONParser();

    public String registerNewUser(String username, String nickname, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "register");
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        jsonObject.put("nickname", nickname);
        String result = "";
        try {
            AppClient.dataOutputStream.writeUTF(jsonObject.toJSONString());
            AppClient.dataOutputStream.flush();
            result = AppClient.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonInput = new JSONObject();
        try {
            jsonInput = (JSONObject) parser.parse(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (jsonInput.get("type").equals("Successful")) {
            new User(username, nickname, password);
            AppClient.setToken(jsonInput.get("token").toString());
            return "success";
        }
        else if (jsonInput.get("message").equals("User with username Exist")){
            return "username!";
        }
        else{
            return "nickname!";
        }
    }

    public String loginUser(String username, String password) {
        String result = "";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "login");
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        try {
            AppClient.dataOutputStream.writeUTF(jsonObject.toJSONString());
            AppClient.dataOutputStream.flush();
            result = AppClient.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonInput = new JSONObject();
        try {
            jsonInput = (JSONObject) parser.parse(result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(jsonInput.toJSONString());
        if (jsonInput.get("type").equals("Successful")) {
            AppClient.setToken(jsonInput.get("token").toString());
            return "success";
        }
        else if (jsonInput.get("message").equals("wrong password")){
            return "pass";
        }
        else if (jsonInput.get("message").equals("user already logged in")){
            return "user";
        }
        return "";
    }

    public boolean isUsernameAndPasswordMatch(String username, String password) {
        if (User.getUserByUsername(username) == null) {
            return false;
        }
        User user = User.getUserByUsername(username);
        assert user != null;
        return user.getPassword().equals(password);
    }


}
