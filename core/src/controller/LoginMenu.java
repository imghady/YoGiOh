package controller;

import model.user.User;
import view.menu.LoginMenuView;
import view.menu.MainMenuView;

import java.util.Objects;

public class LoginMenu {

    public void registerNewUser(String username, String nickname, String password) {
        new User(username, nickname, password);
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
