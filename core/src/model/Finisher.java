package model;

import com.google.gson.Gson;
import model.user.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Finisher {
    public static void finish() throws IOException {
        ArrayList<User> allUsers = User.getAllUsers();
        for (User user : allUsers) {

            Gson gson = new Gson();
            String fileAddress = "src/main/resources/users/" + user.getUsername() + ".json";

            try (FileWriter writer = new FileWriter(fileAddress)) {
                gson.toJson(user, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
