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
    private User currentUser;

    public ProfileMenu(String username) {
        currentUser = User.getUserByUsername(username);
    }

    public void profileChangeNickname(String nickname) {
        if (!canChangeNickname(nickname)) {
            TerminalOutput.output("user with nickname " + nickname + " already exists");
            return;
        }
        String fileAddress = "resources/users/" + currentUser.getUsername() + ".json";
        currentUser.setNickname(nickname);
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(fileAddress)) {
            Object obj = jsonParser.parse(reader);
            JSONObject userData = (JSONObject) obj;
            userData.put("nickname", nickname);
            try (FileWriter file = new FileWriter(fileAddress)) {
                file.write(userData.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        TerminalOutput.output("nickname changed successfully");
    }

    public void profileChangePassword(String currentPassword, String newPassword) {
        if (isPasswordWrong(currentPassword)) {
            TerminalOutput.output("current password invalid");
            return;
        }
        if (currentPassword.equals(newPassword)) {
            TerminalOutput.output("please enter new password");
            return;
        }
        currentUser.setPassword(newPassword);

        String fileAddress = "resources/users/" + currentUser.getUsername() + ".json";
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(fileAddress)) {
            Object obj = jsonParser.parse(reader);
            JSONObject userData = (JSONObject) obj;
            userData.put("password", newPassword);
            try (FileWriter file = new FileWriter(fileAddress)) {
                file.write(userData.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        TerminalOutput.output("password changed successfully!");
    }

    public boolean canChangeNickname(String nickname) {
        return User.getUserByNickname(nickname) == null;
    }

    public boolean isPasswordWrong(String password) {
        return !currentUser.getPassword().equals(password);
    }

}
