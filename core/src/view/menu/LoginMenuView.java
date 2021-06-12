package view.menu;

import controller.LoginMenu;
import model.user.User;
import view.CommandMatcher;
import view.ScanInput;
import view.TerminalOutput;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuView {

    private static boolean isCreateInputCompressed = true;
    private static boolean isLoginInputCompressed = true;

    public static boolean isCreateInputCompressed() {
        return isCreateInputCompressed;
    }

    public static void setIsCreateInputCompressed(boolean isCreateInputCompressed) {
        LoginMenuView.isCreateInputCompressed = isCreateInputCompressed;
    }

    public static boolean isIsLoginInputCompressed() {
        return isLoginInputCompressed;
    }

    public static void setIsLoginInputCompressed(boolean isLoginInputCompressed) {
        LoginMenuView.isLoginInputCompressed = isLoginInputCompressed;
    }

    public void run() {
        String input;
        while (true) {
            input = ScanInput.getInput();
            if (input.matches("menu exit")) {
                break;
            } else if (input.matches("menu show-current")) {
                TerminalOutput.output("LoginView");
            } else if (isUserCreateInputValid(input)) {
                createNewUser(input);
            } else if (isUserLoginInputValid(input)) {
                loginUser(input);
            } else {
                TerminalOutput.output("invalid command");
            }
        }
    }

    public void loginUser(String input) {
        String username;
        String password;
        if (isIsLoginInputCompressed()) {
            username = getUsernameFromCompressedInput(input);
            password = getPasswordFromCompressedInput(input);
        } else {
            username = getUsernameFromInput(input);
            password = getPasswordFromInput(input);
            setIsLoginInputCompressed(true);
        }
        LoginMenu loginMenu = new LoginMenu();
        loginMenu.loginUser(username, password);
    }

    public static boolean isUserCreateInputValid(String userCreateInput) {
        if (userCreateInput.matches("^user create (.+)")) {
            if (userCreateInput.split(" ").length == 8) {
                if (isThisRegexExistInInput(userCreateInput, "(--username [^ ]+)")) {
                    if (isThisRegexExistInInput(userCreateInput, "(--password [^ ]+)")) {
                        if (isThisRegexExistInInput(userCreateInput, "(--nickname [^ ]+)")) {
                            setIsCreateInputCompressed(false);
                            return true;
                        }
                    }
                }
                if (isThisRegexExistInInput(userCreateInput, "(-u [^ ]+)")) {
                    if (isThisRegexExistInInput(userCreateInput, "(-p [^ ]+)")) {
                        return isThisRegexExistInInput(userCreateInput, "(-n [^ ]+)");
                    }
                }
            }
        }
        return false;
    }

    public static boolean isUserLoginInputValid(String userLoginInput) {
        if (userLoginInput.matches("^user login (.+)")) {
            if (userLoginInput.split(" ").length == 6) {
                if (isThisRegexExistInInput(userLoginInput, "(--username [^ ]+)")) {
                    if (isThisRegexExistInInput(userLoginInput, "(--password [^ ]+)")) {
                        setIsLoginInputCompressed(false);
                        return true;
                    }
                }
                if (isThisRegexExistInInput(userLoginInput, "(-u [^ ]+)")) {
                    return isThisRegexExistInInput(userLoginInput, "(-p [^ ]+)");
                }
            }
        }
        return false;
    }

    public static void createNewUser(String input) {
        String username;
        String nickname;
        String password;
        if (isCreateInputCompressed()) {
            username = getUsernameFromCompressedInput(input);
            nickname = getNicknameFromCompressedInput(input);
            password = getPasswordFromCompressedInput(input);
        } else {
            username = getUsernameFromInput(input);
            nickname = getNicknameFromInput(input);
            password = getPasswordFromInput(input);
            setIsCreateInputCompressed(true);
        }
        if (User.getUserByUsername(username) == null) {
            if (User.getUserByNickname(nickname) == null) {
                TerminalOutput.output("user created successfully!");
                LoginMenu loginMenu = new LoginMenu();
                loginMenu.registerNewUser(username, nickname, password);
            } else {
                TerminalOutput.output("user with nickname " + nickname + " already exists");
            }
        } else {
            TerminalOutput.output("user with username " + username + " already exists");
        }
    }

    private static boolean isThisRegexExistInInput(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    private static String getUsernameFromInput(String input) {
        Pattern pattern = Pattern.compile("--username ([^ ]+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static String getUsernameFromCompressedInput(String input) {
        Pattern pattern = Pattern.compile("-u ([^ ]+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static String getPasswordFromInput(String input) {
        Pattern pattern = Pattern.compile("--password ([^ ]+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static String getPasswordFromCompressedInput(String input) {
        Pattern pattern = Pattern.compile("-p ([^ ]+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static String getNicknameFromInput(String input) {
        Pattern pattern = Pattern.compile("--nickname ([^ ]+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static String getNicknameFromCompressedInput(String input) {
        Pattern pattern = Pattern.compile("-n ([^ ]+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static void printNonMatchUsernameAndPasswordError() {
        TerminalOutput.output("Username and password didn’t match!");
    }

    public static void printSuccessfulLogin() {
        TerminalOutput.output("user logged in successfully!");
    }
}
