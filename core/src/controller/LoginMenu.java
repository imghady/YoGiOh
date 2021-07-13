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
            return "success";
        }
        else if (jsonInput.get("message").equals("User with username Exist")){
            return "username!";
        }
        else{
            return "nickname!";
        }
    }

    public void loginUser(String username, String password) {
        if (isUsernameAndPasswordMatch(username, password)) {
            LoginMenuView.printSuccessfulLogin();
            Objects.requireNonNull(User.getUserByUsername(username)).setUserLoggedIn(true);
            MainMenuView mainMenuView = new MainMenuView(username);
            mainMenuView.mainMenuRun();
        } else {
            LoginMenuView.printNonMatchUsernameAndPasswordError();
        }
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
